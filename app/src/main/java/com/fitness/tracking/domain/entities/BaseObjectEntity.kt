package com.fitness.tracking.domain.entities

import com.google.gson.annotations.SerializedName
import java.util.*

open class BaseObjectEntity {
    @SerializedName("id")
    var id: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var message: String? = null
    var foo: String? = null
    var count: Long = 0
    var code = 0
}