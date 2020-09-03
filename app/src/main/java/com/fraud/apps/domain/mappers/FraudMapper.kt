package com.fraud.apps.domain.mappers

import com.fraud.apps.domain.entities.response.FraudEntity
import com.fraud.apps.domain.exceptions.MapperException
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.utils.FormatterDate

class FraudMapper : BaseMapper<FraudEntity, Fraud>() {
    override fun createObject(): Fraud {
        return Fraud()
    }

    override fun createEntity(): FraudEntity {
        return FraudEntity()
    }

    @Throws(MapperException::class)
    override fun defineObject(`object`: Fraud?): Fraud {
        `object`?.id = baseEntity?.id
        `object`?.reportId = baseEntity?.reportId
        `object`?.fraudType = baseEntity?.fraudType
        `object`?.totalLoss = baseEntity?.totalLoss!!
        `object`?.cityVictim = baseEntity?.cityVictim
        `object`?.createdAt = FormatterDate.formatInd.format(baseEntity?.createdAt)
        `object`?.updatedAt = FormatterDate.formatInd.format(baseEntity?.updatedAt)
        return `object`!!
    }

    @Throws(MapperException::class)
    override fun defineEntity(entity: FraudEntity?): FraudEntity {
        return entity!!
    }
}