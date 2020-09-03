package com.fraud.apps.data.local.dao

import android.content.ContentValues
import android.content.Context
import com.fraud.apps.data.local.table.TableReport
import com.fraud.apps.domain.models.Report

class DaoReport(context: Context) : BaseDao<Report>(context) {

    override fun values(t: Report): ContentValues? {
        val values = ContentValues()
        try {
            values.put(TableReport.COLUMN_ID, t.id)
            values.put(TableReport.COLUMN_NUMBER, t.number)
            if (t.isAccountNumber!!) {
                values.put(TableReport.COLUMN_TYPE_NUMBER, 1)
            } else {
                values.put(TableReport.COLUMN_TYPE_NUMBER, 0)
            }
            values.put(TableReport.COLUMN_CREATED_AT, t.createdAt)
            values.put(TableReport.COLUMN_UPDATED_AT, t.updatedAt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return values
    }

    override fun cursorData(): Report {
        var report = Report();
        report.id = sourceCursor.getCursorString(TableReport.COLUMN_ID)
        report.number = sourceCursor.getCursorString(TableReport.COLUMN_NUMBER)
        report.isPhoneNumber = sourceCursor.getCursorInt(TableReport.COLUMN_TYPE_NUMBER) != 1
        report.isAccountNumber = sourceCursor.getCursorInt(TableReport.COLUMN_TYPE_NUMBER) == 1
        report.createdAt = sourceCursor.getCursorString(TableReport.COLUMN_CREATED_AT)
        report.updatedAt = sourceCursor.getCursorString(TableReport.COLUMN_UPDATED_AT)
        return report
    }

    init {
        tableName = TableReport.TABLE_NAME
        allColumn = arrayOf(
                TableReport.COLUMN_ID,
                TableReport.COLUMN_NUMBER,
                TableReport.COLUMN_TYPE_NUMBER,
                TableReport.COLUMN_CREATED_AT,
                TableReport.COLUMN_UPDATED_AT
        )
    }
}