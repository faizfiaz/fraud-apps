package com.fraud.apps.data.remote

import com.fraud.apps.domain.entities.ReportEntity
import com.fraud.apps.domain.entities.requests.FraudRequest
import com.fraud.apps.domain.entities.requests.ReportRequest
import com.fraud.apps.domain.entities.response.FraudEntity
import io.reactivex.Single
import retrofit2.http.*

/**
 * API Backend Service
 */
interface RemoteAPI {

    @GET("/reports")
    fun getReports(): Single<List<ReportEntity>?>

    @POST("/reports")
    fun reports(@Body reportRequest: ReportRequest): Single<ReportEntity?>

    @GET("reports/{id}/frauds")
    fun getFrauds(@Path("id") reportId: String): Single<List<FraudEntity>?>

    @POST("reports/{id}/frauds")
    fun frauds(@Path("id") reportId: String, @Body fraudRequest: FraudRequest): Single<FraudEntity?>

    @PUT("reports/{id}/frauds/{fraudId}")
    fun frauds(@Path("id") reportId: String, @Path("fraudId") fraudId: String, @Body fraudRequest: FraudRequest): Single<FraudEntity?>

}