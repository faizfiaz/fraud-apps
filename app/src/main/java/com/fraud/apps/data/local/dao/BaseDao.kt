package com.fraud.apps.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.fraud.apps.data.local.HelperDb
import com.fraud.apps.domain.models.BaseObject

open class BaseDao<T : BaseObject>(context: Context) {
    var allColumn: Array<String>? = null
    var tableName: String? = null
    protected var sourceCursor = SourceCursor()
    private var database: SQLiteDatabase? = null

    var helperDb: HelperDb? = null

    private fun instanceDBName(context: Context): String {
        var dbName = ""
        dbName += dBName
        return dbName
    }

    private val dBName: String
        get() = HelperDb.DB_NAME

    fun read() {
        database = helperDb?.readableDatabase
    }

    fun write() {
        try {
            database = helperDb?.writableDatabase
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun closeDatabase() {
        if (database != null) {
            database!!.close()
        }
    }

    fun createData(t: T): Long {
        var result: Long = 0
        try {
            result = database!!.insert(tableName, null, values(t))
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        return result
    }

    fun createData(t: List<T>): Long {
        var result: Long = 0
        t.forEach {
            val condition = "id=${it.id}"
            if (getData(condition) == null) {
                val res = createData(it)
                if (res > 0) {
                    result++
                }
            } else {
                var res = update(it, condition)
                if (res > 0) {
                    result++
                }
            }
        }
        return result
    }

    open fun values(t: T): ContentValues? {
        return null
    }

    fun getData(condition: String?): T? {
        var cursor: Cursor? = null
        var t: T? = null
        try {
            cursor = database!!.query(true, tableName, allColumn, condition,
                    null, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    sourceCursor.setCursor(cursor)
                    t = cursorData()
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor!!.close()
        }
        return t
    }

    fun getDataByOrder(orderBy: String?): T? {
        var cursor: Cursor? = null
        var t: T? = null
        try {
            cursor = database!!.query(true, tableName, allColumn, null,
                    null, null, null, orderBy, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    sourceCursor.setCursor(cursor)
                    t = cursorData()
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return t
    }

    open fun cursorData(): T? {
        return null
    }

    fun getAllData(condition: String?,
                   orderBy: String?, limit: String?, groupby: String?): ArrayList<T?> {
        val allData = ArrayList<T?>()
        var cursor: Cursor? = null
        try {
            cursor = database!!.query(tableName, allColumn, condition, null, groupby,
                    null, orderBy, limit)
            if (cursor != null && cursor.moveToFirst() && cursor.count >= 1) {
                do {
                    sourceCursor.setCursor(cursor)
                    val t = cursorData()
                    allData.add(t)
                } while (cursor.moveToNext())
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return allData
    }

    fun update(t: T, condition: String?): Long {
        var result: Long = 0
        try {
            result = database!!.update(tableName, values(t), condition, null).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun delete(condition: String?): Int {
        return database!!.delete(tableName, condition, null)
    }

    fun rawData(rawQuery: String?): T? {
        var cursor: Cursor? = null
        var t: T? = null
        try {
            cursor = database!!.rawQuery(rawQuery, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    sourceCursor.setCursor(cursor)
                    t = cursorData()
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor!!.close()
        }
        return t
    }

    fun rawList(rawQuery: String?): ArrayList<T?> {
        val allData = ArrayList<T?>()
        var cursor: Cursor?
        try {
            cursor = database!!.rawQuery(rawQuery, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    sourceCursor.setCursor(cursor)
                    val t = cursorData()
                    allData.add(t)
                } while (cursor.moveToNext())
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return allData
    }

    fun countLength(condition: String?, groupBy: String?): Int {
        var count = 0
        var cursor: Cursor? = null
        try {
            cursor = database!!.rawQuery("Select count(*) from " + tableName +
                    conditionCount(condition) + groupBy(groupBy), null)
            if (cursor != null) {
                cursor.moveToFirst()
                count = cursor.count
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return count
    }

    fun count(condition: String?): Int {
        var count = 0
        var cursor: Cursor? = null
        try {
            cursor = database!!.rawQuery("Select count(*) from " + tableName +
                    conditionCount(condition), null)
            if (cursor != null) {
                cursor.moveToFirst()
                count = cursor.getInt(0)
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return count
    }

    private fun groupBy(groupBy: String?): String {
        return if (groupBy != null && groupBy.isNotEmpty()) {
            (" GROUP BY "
                    + groupBy)
        } else ""
    }

    private fun conditionCount(condition: String?): String {
        return if (condition != null && condition.length > 0) {
            (" WHERE "
                    + condition)
        } else ""
    }

    init {
        helperDb = HelperDb.instance
    }
}