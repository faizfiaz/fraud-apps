package com.fitness.tracking.domain.entities.requests

import com.google.gson.annotations.SerializedName

data class ReportRequest(
        @SerializedName("number") var number: String? = null,
        @SerializedName("no_telp") var isPhone: Boolean = false,
        @SerializedName("no_rek") var isBankAccount: Boolean = false
)