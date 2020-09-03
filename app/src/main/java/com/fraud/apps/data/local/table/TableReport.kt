package com.fraud.apps.data.local.table

object TableReport {
    const val TABLE_NAME = "report"
    const val COLUMN_ID = "id"
    const val COLUMN_NUMBER = "number"
    const val COLUMN_TYPE_NUMBER = "isBankAccount"
    const val COLUMN_CREATED_AT = "createdAt"
    const val COLUMN_UPDATED_AT = "updatedAt"

    const val SQL_CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COLUMN_ID + " VARCHAR, "
            + COLUMN_NUMBER + " VARCHAR NOT NULL, "
            + COLUMN_TYPE_NUMBER + " SMALLINT NOT NULL, "
            + COLUMN_CREATED_AT + " VARCHAR, "
            + COLUMN_UPDATED_AT + " VARCHAR"
            + ")")
}