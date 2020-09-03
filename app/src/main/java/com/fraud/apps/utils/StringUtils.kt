package com.fraud.apps.utils

import java.text.*
import java.util.*

object StringUtils {
    var FORMAT_DD_MM_YYYY = "dd.MM.yyyy"
    var FORMAT_DD_MM_YY = "dd.MM.yy"
    fun convertStringToDate(dateFromDB: String): Date? {
        return if (isBlank(dateFromDB)) {
            null
        } else try {
            val subDate = dateFromDB.substring(0, 10)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)
            formatter.parse(subDate)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun convertDateToString(date: Date?): String {
        val dateFormat: DateFormat = SimpleDateFormat(FORMAT_DD_MM_YY, Locale.GERMAN)
        return dateFormat.format(date)
    }

    fun convertDateToString(date: Date?, format: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat(format, Locale.GERMAN)
        return dateFormat.format(date)
    }

    fun beautifulNumber(number: Long?): String {
        val formatter: NumberFormat = DecimalFormat("###,###,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
        val numberStr = formatter.format(number)
        return numberStr.substring(0, numberStr.length - 3)
    }

    fun isBlank(string: String?): Boolean {
        return string == null || string.isEmpty()
    }

    fun isNotBlank(string: String?): Boolean {
        return !(string == null || string.isEmpty())
    }

    fun convertNumberToDays(number: String, fullName: Boolean): String {
        var valueNumber = number
        valueNumber = valueNumber.replace("|", "").trim { it <= ' ' }
        val splitNumber = valueNumber.split("").toTypedArray()
        if (splitNumber.isNotEmpty()) {
            if (!isBlank(splitNumber[1]) && !isBlank(splitNumber[splitNumber.size - 1])) {
                return String.format("%s - %s", getDaysName(splitNumber[1], fullName), getDaysName(splitNumber[splitNumber.size - 1], fullName))
            }
        }
        return "Closed"
    }

    private fun getDaysName(number: String, fullName: Boolean): String {
        try {
            val days = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            return if (fullName) {
                days[number.toInt()]
            } else {
                days[number.toInt()].substring(0, 3)
            }
        } catch (ignored: ArrayIndexOutOfBoundsException) {
        }
        return "Unknown day"
    }

    fun convertSnakeCase(string: String): String? {
        return capitalize(string.replace("_", " "))
    }

    fun capitalize(str: String?): String? {
        return if (str == null || str.isEmpty()) {
            str
        } else str.substring(0, 1).toUpperCase() + str.substring(1)
    }
}