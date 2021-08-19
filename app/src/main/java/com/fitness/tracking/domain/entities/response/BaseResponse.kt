package com.fitness.tracking.domain.entities.response

import com.fitness.tracking.domain.entities.BaseObjectEntity

open class BaseResponse<E> : BaseObjectEntity() {
    var data: E? = null
    var error: String? = null

}


