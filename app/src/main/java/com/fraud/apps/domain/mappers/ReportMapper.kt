package com.fraud.apps.domain.mappers

import com.fraud.apps.domain.entities.ReportEntity
import com.fraud.apps.domain.exceptions.MapperException
import com.fraud.apps.domain.models.Report
import com.fraud.apps.utils.FormatterDate

class ReportMapper : BaseMapper<ReportEntity?, Report?>() {
    override fun createObject(): Report? {
        return Report()
    }

    override fun createEntity(): ReportEntity? {
        return ReportEntity()
    }

    @Throws(MapperException::class)
    override fun defineObject(`object`: Report?): Report? {
        `object`?.id = baseEntity?.id
        `object`?.number = baseEntity?.number
        `object`?.isAccountNumber = baseEntity?.isAccountNumber
        `object`?.isPhoneNumber = baseEntity?.isPhoneNumber
        `object`?.createdAt = FormatterDate.formatInd.format(baseEntity?.createdAt)
        `object`?.updatedAt = FormatterDate.formatInd.format(baseEntity?.updatedAt)
        return `object`
    }

    @Throws(MapperException::class)
    override fun defineEntity(entity: ReportEntity?): ReportEntity? {
        return entity
    }
}