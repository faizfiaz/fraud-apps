package com.fitness.tracking.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fitness.tracking.App
import com.fitness.tracking.data.local.table.TableFraud
import com.fitness.tracking.data.local.table.TableReport

class HelperDb constructor(context: Context?, private val dbName: String)
    : SQLiteOpenHelper(context, dbName, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TableReport.SQL_CREATE_TABLE)
        db.execSQL(TableFraud.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        var DATABASE_VERSION = 1
        var DB_NAME = "fraud.db"
        var instance: HelperDb? = null
            get() {
                val context: Context? = App.appContext
                if (field == null) {
                    field = HelperDb(context, DB_NAME)
                }
                return field
            }
            private set
    }

}