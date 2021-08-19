package com.fitness.tracking.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Report(
        @SerializedName("number") var number: String? = null,
        @SerializedName("no_telp") var isPhoneNumber: Boolean? = false,
        @SerializedName("no_rek") var isAccountNumber: Boolean? = false
) : BaseObject(), Serializable