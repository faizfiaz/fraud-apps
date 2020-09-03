package com.fraud.apps.data.remote

import com.fraud.apps.domain.entities.ReportEntity
import com.fraud.apps.domain.entities.requests.FraudRequest
import com.fraud.apps.domain.entities.requests.ReportRequest
import com.fraud.apps.domain.entities.response.FraudEntity
import com.fraud.apps.domain.entities.response.UserResponse
import io.reactivex.Single

class AppRepository private constructor() : BaseRepository<UserResponse?>() {

    fun getReports(): Single<List<ReportEntity>?>? {
        return remoteAPI.getReports()
    }

    fun getAllFrauds(reportId: String): Single<List<FraudEntity>?>? {
        return remoteAPI.getFrauds(reportId)
    }

    fun addReports(reportRequest: ReportRequest): Single<ReportEntity?> {
        return remoteAPI.reports(
                reportRequest)
    }

    fun addFraud(reportId: String, fraudRequest: FraudRequest): Single<FraudEntity?> {
        return remoteAPI.frauds(
                reportId,
                fraudRequest)
    }

    fun editFraud(reportId: String, fraudId: String, fraudRequest: FraudRequest): Single<FraudEntity?> {
        return remoteAPI.frauds(
                reportId,
                fraudId,
                fraudRequest)
    }

    companion object {
        @JvmStatic
        var instance: AppRepository? = null
            get() {
                if (field == null) {
                    field = AppRepository()
                }
                return field
            }
            private set
    }

    override fun get(): Single<List<UserResponse?>?>? {
        return null
    }

    override fun getById(id: Int): Single<List<UserResponse?>?>? {
        return null
    }

}