package com.fitness.tracking.domain.entities

import com.google.gson.annotations.SerializedName

data class ReportEntity(
        @SerializedName("number") val number: String? = null,
        @SerializedName("no_telp") val isPhoneNumber: Boolean? = false,
        @SerializedName("no_rek") val isAccountNumber: Boolean? = false
) : BaseObjectEntity()