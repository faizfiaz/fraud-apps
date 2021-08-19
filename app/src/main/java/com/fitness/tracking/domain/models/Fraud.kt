package com.fitness.tracking.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Fraud(
        @SerializedName("reportId") var reportId: String? = null,
        @SerializedName("jenis_penipuan") var fraudType: String? = null,
        @SerializedName("jumlah_kerugian") var totalLoss: Double = 0.0,
        @SerializedName("kota_korban") var cityVictim: String? = null
) : BaseObject(), Serializable