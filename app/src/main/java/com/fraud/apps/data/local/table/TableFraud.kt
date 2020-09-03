package com.fraud.apps.data.local.table

object TableFraud {
    const val TABLE_NAME = "fraud"
    const val COLUMN_ID = "id"
    const val COLUMN_REPORT_ID = "reportId"
    const val COLUMN_TYPE_FRAUD = "typeFraud"
    const val COLUMN_TOTAL_LOSS = "totalLoss"
    const val COLUMN_CITY = "city"
    const val COLUMN_CREATED_AT = "createdAt"
    const val COLUMN_UPDATED_AT = "updatedAt"

    const val SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COLUMN_ID + " VARCHAR, "
            + COLUMN_REPORT_ID + " VARCHAR, "
            + COLUMN_TYPE_FRAUD + " VARCHAR NOT NULL, "
            + COLUMN_TOTAL_LOSS + " DECIMAL NOT NULL, "
            + COLUMN_CITY + " BIGINT NOT NULL, "
            + COLUMN_CREATED_AT + " VARCHAR NOT NULL, "
            + COLUMN_UPDATED_AT + " VARCHAR NOT NULL"
            + ")")
}