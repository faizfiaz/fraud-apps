package com.fraud.apps.domain.usecases.user

import com.fraud.apps.data.remote.AppRepository
import com.fraud.apps.domain.exceptions.MapperException
import com.fraud.apps.domain.mappers.ReportMapper
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.domain.models.Report
import com.fraud.apps.domain.usecases.base.BaseUsecase
import com.fraud.apps.ui.home.HomeNavigator
import io.reactivex.Single

abstract class IAppUsecases(mapper: ReportMapper, appRepository: AppRepository?) :
        BaseUsecase<ReportMapper, AppRepository>(mapper, appRepository) {

    abstract fun checkToken(): Boolean

    /*Report Function*/
    @Throws(MapperException::class)
    abstract suspend fun getReportList(): Any
    abstract suspend fun saveData(list: List<Report>): Long
    abstract suspend fun getReportLocal(page: Int): List<Report?>
    abstract suspend fun getTotalItems(): Int

    abstract suspend fun addReport(number: String, isAccountNumber: Boolean): Single<Any?>

    /*Fraud Function*/
    abstract suspend fun getAllFraudList(callbackFraud: HomeNavigator.CallbackFraud): Any

    abstract suspend fun getCountAccountOrPhone(): Int
    abstract suspend fun getLatestFraud(): Double
    abstract suspend fun getFraudById(id: String): List<Fraud?>

    abstract suspend fun addFraud(reportId: String,
                                  typeFraud: String,
                                  totalLoss: String,
                                  city: String,
                                  edit: Boolean,
                                  fraudId: String): Single<Any?>


}