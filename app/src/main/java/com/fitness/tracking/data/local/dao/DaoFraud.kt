package com.fitness.tracking.data.local.dao

import android.content.ContentValues
import android.content.Context
import com.fitness.tracking.data.local.table.TableFraud
import com.fitness.tracking.domain.models.Fraud

class DaoFraud(context: Context) : BaseDao<Fraud>(context) {

    override fun values(t: Fraud): ContentValues? {
        val values = ContentValues()
        try {
            values.put(TableFraud.COLUMN_ID, t.id)
            values.put(TableFraud.COLUMN_REPORT_ID, t.reportId)
            values.put(TableFraud.COLUMN_TYPE_FRAUD, t.fraudType)
            values.put(TableFraud.COLUMN_TOTAL_LOSS, t.totalLoss)
            values.put(TableFraud.COLUMN_CITY, t.cityVictim)
            values.put(TableFraud.COLUMN_CREATED_AT, t.createdAt)
            values.put(TableFraud.COLUMN_UPDATED_AT, t.updatedAt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return values
    }

    override fun cursorData(): Fraud {
        var fraud = Fraud()
        fraud.id = sourceCursor.getCursorString(TableFraud.COLUMN_ID)
        fraud.reportId = sourceCursor.getCursorString(TableFraud.COLUMN_REPORT_ID)
        fraud.fraudType = sourceCursor.getCursorString(TableFraud.COLUMN_TYPE_FRAUD)
        fraud.totalLoss = sourceCursor.getCursorDouble(TableFraud.COLUMN_TOTAL_LOSS)
        fraud.cityVictim = sourceCursor.getCursorString(TableFraud.COLUMN_CITY)
        fraud.createdAt = sourceCursor.getCursorString(TableFraud.COLUMN_CREATED_AT)
        fraud.updatedAt = sourceCursor.getCursorString(TableFraud.COLUMN_UPDATED_AT)
        return fraud
    }

    init {
        tableName = TableFraud.TABLE_NAME
        allColumn = arrayOf(
                TableFraud.COLUMN_ID,
                TableFraud.COLUMN_REPORT_ID,
                TableFraud.COLUMN_TYPE_FRAUD,
                TableFraud.COLUMN_TOTAL_LOSS,
                TableFraud.COLUMN_CITY,
                TableFraud.COLUMN_CREATED_AT,
                TableFraud.COLUMN_UPDATED_AT
        )
    }
}