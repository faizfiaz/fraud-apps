package com.fitness.tracking.domain.mappers

import com.fitness.tracking.domain.entities.BaseObjectEntity
import com.fitness.tracking.domain.exceptions.MapperException
import com.fitness.tracking.domain.models.BaseObject
import java.util.*

abstract class BaseMapper<E : BaseObjectEntity?, O : BaseObject?> {
    var baseEntity: E? = null
    var `object`: O? = null

    @Throws(MapperException::class)
    fun convertToObject(entity: E): O {
        return try {
            this.baseEntity = entity
            `object` = createObject()
            `object`?.id = (entity!!.id)
            defineObject(`object`)
        } catch (e: Exception) {
            throw MapperException(e.message)
        }
    }

    @Throws(MapperException::class)
    fun convertToEntity(`object`: O): E {
        return try {
            baseEntity = createEntity()
            baseEntity?.id = (`object`!!.id)
            defineEntity(baseEntity)
        } catch (e: Exception) {
            throw MapperException(e.message)
        }
    }

    @Throws(MapperException::class)
    fun convertToObjectList(entityList: List<E>): List<O> {
        val list: MutableList<O> = ArrayList()
        for (entity in entityList) {
            list.add(convertToObject(entity))
        }
        return list
    }

    @Throws(MapperException::class)
    fun convertToEntityList(objectList: List<O>): List<E> {
        val list: MutableList<E> = ArrayList()
        for (`object` in objectList) {
            list.add(convertToEntity(`object`))
        }
        return list
    }

    abstract fun createObject(): O
    abstract fun createEntity(): E

    @Throws(MapperException::class)
    abstract fun defineObject(`object`: O?): O

    @Throws(MapperException::class)
    abstract fun defineEntity(entity: E?): E
}