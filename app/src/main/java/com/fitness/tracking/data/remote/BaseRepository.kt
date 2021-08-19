package com.fitness.tracking.data.remote

import com.fitness.tracking.domain.entities.BaseObjectEntity
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
abstract class BaseRepository<E : BaseObjectEntity?> {
    protected var entities: List<E> = arrayListOf()
    protected var remoteAPI: RemoteAPI = RetrofitFactory.instance!!.remoteAPI
    abstract fun get(): Single<List<E>?>?
    abstract fun getById(id: Int): Single<List<E>?>?

    companion object {
        private val instance: BaseRepository<*>? = null
    }

}