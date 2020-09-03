package com.fraud.apps.domain.entities.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("email")
        var identifier: String? = null,
        @SerializedName("password")
        var password: String? = null
)