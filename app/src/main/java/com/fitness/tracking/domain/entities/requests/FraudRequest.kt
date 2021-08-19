package com.fitness.tracking.domain.entities.requests

import com.google.gson.annotations.SerializedName

data class FraudRequest(
        @SerializedName("jenis_penipuan") var typeFraud: String? = null,
        @SerializedName("jumlah_kerugian") var totalLoss: String? = null,
        @SerializedName("kota_korban") var city: String? = null,
        var createdAt: String? = null,
        var updatedAt: String? = null
)