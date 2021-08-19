package com.fitness.tracking.data.remote

import com.fitness.tracking.domain.entities.ReportEntity
import com.fitness.tracking.domain.entities.requests.FraudRequest
import com.fitness.tracking.domain.entities.requests.ReportRequest
import com.fitness.tracking.domain.entities.response.FraudEntity
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