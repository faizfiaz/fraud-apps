package com.fitness.tracking.data.local.dao

import android.database.Cursor

class SourceCursor {
    private var cursor: Cursor? = null
    fun setCursor(cursor: Cursor?) {
        this.cursor = cursor
    }

    fun getCursorString(field: String): String {
        return cursor!!.getString(columnIndex(field))
    }

    fun getCursorInt(field: String): Int {
        return cursor!!.getInt(columnIndex(field))
    }

    fun getCursorDouble(field: String): Double {
        return cursor!!.getDouble(columnIndex(field))
    }

    fun getCursorFloat(field: String): Float {
        return cursor!!.getFloat(columnIndex(field))
    }

    fun getCursorLong(field: String): Long {
        return cursor!!.getLong(columnIndex(field))
    }

    fun getCursorBytearray(field: String): ByteArray {
        return cursor!!.getBlob(columnIndex(field))
    }

    private fun columnIndex(field: String): Int {
        return cursor!!.getColumnIndex(field)
    }
}