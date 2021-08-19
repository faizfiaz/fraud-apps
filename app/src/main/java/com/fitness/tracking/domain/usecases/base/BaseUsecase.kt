package com.fitness.tracking.domain.usecases.base

import com.fitness.tracking.data.remote.BaseRepository
import com.fitness.tracking.data.remote.AppRepository
import com.fitness.tracking.domain.mappers.BaseMapper

abstract class BaseUsecase<M : BaseMapper<*, *>?, R :
BaseRepository<*>?>(protected var mapper: M, protected var repository: AppRepository?) {

    fun isErrorCode(statusCode: Int): Boolean {
        if (statusCode > 200) {
            return true
        }
        return false
    }
}