package com.fraud.apps.domain.entities.requests

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
        @SerializedName("username")
        private val username: String? = null,
        @SerializedName("email")
        private val email: String? = null,
        @SerializedName("password")
        private val password: String? = null
)