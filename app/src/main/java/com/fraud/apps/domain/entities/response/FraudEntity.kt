package com.fraud.apps.domain.entities.response

import com.fraud.apps.domain.entities.BaseObjectEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FraudEntity(
        @SerializedName("reportId") var reportId: String? = null,
        @SerializedName("jenis_penipuan") var fraudType: String? = null,
        @SerializedName("jumlah_kerugian") var totalLoss: Double = 0.0,
        @SerializedName("kota_korban") var cityVictim: String? = null
) : BaseObjectEntity(), Serializable