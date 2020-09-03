package com.fraud.apps.domain.entities.response

import com.fraud.apps.domain.entities.BaseObjectEntity

open class BaseResponse<E> : BaseObjectEntity() {
    var data: E? = null
    var error: String? = null

}


