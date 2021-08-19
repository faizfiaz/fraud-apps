package com.fitness.tracking.domain.usecases.user

import com.fitness.tracking.App
import com.fitness.tracking.data.local.PreferencesManager
import com.fitness.tracking.data.local.dao.DaoFraud
import com.fitness.tracking.data.local.dao.DaoReport
import com.fitness.tracking.data.local.table.TableFraud
import com.fitness.tracking.data.local.table.TableReport
import com.fitness.tracking.data.remote.AppRepository
import com.fitness.tracking.domain.entities.ReportEntity
import com.fitness.tracking.domain.entities.requests.FraudRequest
import com.fitness.tracking.domain.entities.requests.ReportRequest
import com.fitness.tracking.domain.entities.response.FraudEntity
import com.fitness.tracking.domain.mappers.FraudMapper
import com.fitness.tracking.domain.mappers.ReportMapper
import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.ui.home.HomeNavigator
import com.fitness.tracking.utils.FormatterDate
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*
import kotlin.collections.ArrayList

open class AppUsecases(mapper: ReportMapper, val fraudMapper: FraudMapper, repository: AppRepository?)
    : IAppUsecases(mapper, repository) {

    private var preferencesManager: PreferencesManager? = PreferencesManager.instance
    private var daoReport = DaoReport(App.appContext!!)
    private var daoFraud = DaoFraud(App.appContext!!)

    private val timeLimit = 60000L;

    override fun checkToken(): Boolean {
        return !preferencesManager?.prefToken.isNullOrBlank()
    }

    /*Report Section*/
    /*
    * Only Get Data From API if last updated > 60000ms (60sec)
    * if last update < 60000ms just get from local db
    * */
    override suspend fun getReportList(): Any = withContext(Dispatchers.IO) {
        if (preferencesManager?.prefLastUpdateReport!! == 0L) {
            return@withContext getReportListFromAPI()
        } else {
            val lastUpdate = preferencesManager?.prefLastUpdateReport
            val divideLastUpdate = Date().time - lastUpdate!!
            if (divideLastUpdate < timeLimit) {
                return@withContext Single.just(true)
            } else {
                return@withContext getReportListFromAPI()
            }
        }

    }

    suspend fun getReportListFromAPI() = withContext(Dispatchers.IO) {
        preferencesManager?.prefLastUpdateReport = Date().time
        val response = async { repository?.getReports() }
        try {
            checkResponse(response.await()?.blockingGet())
        } catch (e: Exception) {
            throw java.lang.Exception(e.message)
        }
    }

    override suspend fun saveData(list: List<Report>) = withContext(Dispatchers.IO) {
        daoReport.write()
        daoReport.createData(list)
    }

    override suspend fun getReportLocal(page: Int) = withContext(Dispatchers.IO) {
        daoReport.read()
        daoReport.getAllData("", "", "${page * 10}, 10", "").toList()
    }

    override suspend fun getTotalItems(): Int = withContext(Dispatchers.IO) {
        daoReport.read()
        daoReport.count("")
    }

    private fun checkResponse(response: List<ReportEntity>?): Single<Any?> {
        if (response == null) {
            return Single.just("Something wrong")
        }
        var list = mapper.convertToObjectList(response)
        return Single.just(list)
    }

    override suspend fun addReport(number: String, isAccountNumber: Boolean): Single<Any?> = withContext(Dispatchers.IO) {
        var request = ReportRequest(number, !isAccountNumber, isAccountNumber)
        val response = async { repository?.addReports(request) }
        try {
            checkAddReportResponse(response.await()?.blockingGet())
        } catch (e: Exception) {
            throw java.lang.Exception(e.message)
        }
    }

    private fun checkAddReportResponse(response: ReportEntity?): Single<Any?> {
        if (response == null) {
            return Single.just(false)
        }
        var report = mapper.convertToObject(response)
        daoReport.write()
        daoReport.createData(report!!)
        return Single.just(report)
    }

    /*End Report Section*/

    /*Fraud Section*/
    override suspend fun getAllFraudList(callbackFraud: HomeNavigator.CallbackFraud): Any = withContext(Dispatchers.IO) {
        var totalLoss = 0.0;
        var value: Double
        daoReport.read()
        var reports: List<Report?> = ArrayList()
        try {
            reports = daoReport.getAllData("", "", "", "").toList()
        } catch (e: java.lang.Exception) {
            reports = daoReport.getAllData("", "", "", "").toList()
        }

        reports.forEach {
            value = if (preferencesManager?.prefLastUpdateFraud!! == 0L) {
                try {
                    async { getFraudFromAPI(it?.id!!) }.await() as Double
                } catch (e: java.lang.Exception) {
                    0.0
                }
            } else {
                val lastUpdate = preferencesManager?.prefLastUpdateFraud
                val divideLastUpdate = Date().time - lastUpdate!!
                if (divideLastUpdate < timeLimit) {
                    var getLocalData = async { getFraudLocal(it?.id!!) }
                    var data = getLocalData.await()
                    var totalValue = 0.0
                    data.forEach {
                        if (it != null) {
                            totalValue += it.totalLoss
                        }
                    }
                    totalValue
                } else {
                    try {
                        async { getFraudFromAPI(it?.id!!) }.await() as Double
                    } catch (e: java.lang.Exception) {
                        0.0
                    }

                }
            }
            totalLoss += value
            callbackFraud.updatePrice(totalLoss)
        }
        preferencesManager?.prefLastUpdateFraud = Date().time
    }

    private suspend fun getFraudLocal(id: String) = withContext(Dispatchers.IO) {
        daoFraud.read()
        daoFraud.getAllData("${TableFraud.COLUMN_REPORT_ID}=$id", "", "", "")
    }

    private suspend fun getFraudFromAPI(id: String): Any? = withContext(Dispatchers.IO) {
        try {
            val response = async { repository?.getAllFrauds(id) }
            val result = response.await()?.blockingGet()
            val dataMapping = fraudMapper.convertToObjectList(result!!)
            saveDataFraud(dataMapping)
            result.forEach {
                return@withContext it.totalLoss
            }

        } catch (e: java.lang.Exception) {
            return@withContext 0.0
        }
    }

    private fun saveDataFraud(dataMapping: List<Fraud>) {
        daoFraud.write()
        daoFraud.createData(dataMapping)
    }

    override suspend fun getCountAccountOrPhone(): Int = withContext(Dispatchers.IO) {
        daoReport.read()
        daoReport.count("${TableReport.COLUMN_TYPE_NUMBER}=1")
    }

    override suspend fun getLatestFraud(): Double = withContext(Dispatchers.IO) {
        daoFraud.read()
        val latestFraud = daoFraud.getDataByOrder("ABS(${TableReport.COLUMN_ID}) ASC")
        if (latestFraud != null) {
            return@withContext latestFraud.totalLoss
        } else {
            return@withContext 0.0
        }
    }

    override suspend fun getFraudById(id: String): List<Fraud?> = withContext(Dispatchers.IO) {
        getFraudLocal(id).toList()
    }

    override suspend fun addFraud(reportId: String, typeFraud: String, totalLoss: String,
                                  city: String, edit: Boolean, fraudId: String): Single<Any?> = withContext(Dispatchers.IO) {
        val dateTime = FormatterDate.formatDateTime(Date().time)
        val request: FraudRequest
        request = if (edit) {
            FraudRequest(typeFraud, totalLoss, city, updatedAt = dateTime)
        } else {
            FraudRequest(typeFraud, totalLoss, city, dateTime, dateTime)
        }

        var response: Deferred<Single<FraudEntity?>?>
        response = if (edit) {
            async { repository?.editFraud(reportId, fraudId, request) }
        } else {
            async { repository?.addFraud(reportId, request) }
        }
        try {
            checkAddFraudResponse(response.await()?.blockingGet(), edit)
        } catch (e: HttpException) {
            throw java.lang.Exception(e.response()?.errorBody()?.string())
        }
    }

    private fun checkAddFraudResponse(response: FraudEntity?, edit: Boolean): Single<Any?> {
        if (response == null) {
            return Single.just(false)
        }
        var fraud = fraudMapper.convertToObject(response)
        daoFraud.write()
        if (edit) {
            daoFraud.update(fraud, "${TableFraud.COLUMN_ID}=${fraud.id}")
        } else {
            daoFraud.createData(fraud)
        }
        return Single.just(fraud)
    }
    /*End Fraud Section*/


}