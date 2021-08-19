package com.fitness.tracking.domain.usecases.user

import com.fitness.tracking.data.remote.AppRepository
import com.fitness.tracking.domain.exceptions.MapperException
import com.fitness.tracking.domain.mappers.ReportMapper
import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.domain.usecases.base.BaseUsecase
import com.fitness.tracking.ui.home.HomeNavigator
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