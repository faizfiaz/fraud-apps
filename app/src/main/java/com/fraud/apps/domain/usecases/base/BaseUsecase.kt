package com.fraud.apps.domain.usecases.base

import com.fraud.apps.data.remote.BaseRepository
import com.fraud.apps.data.remote.AppRepository
import com.fraud.apps.domain.mappers.BaseMapper

abstract class BaseUsecase<M : BaseMapper<*, *>?, R :
BaseRepository<*>?>(protected var mapper: M, protected var repository: AppRepository?) {

    fun isErrorCode(statusCode: Int): Boolean {
        if (statusCode > 200) {
            return true
        }
        return false
    }
}