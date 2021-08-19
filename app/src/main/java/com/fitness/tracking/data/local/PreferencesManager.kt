package com.fitness.tracking.data.local

import android.content.Context
import android.content.SharedPreferences
import com.fitness.tracking.App

class PreferencesManager private constructor(context: Context?) {
    private val sharedPreferences: SharedPreferences?
    private val editor: SharedPreferences.Editor?
    private fun setPrefString(key: String, value: String) {
        editor?.putString(key, value)
        editor?.apply()
    }

    private fun getPrefString(key: String, defValue: String): String? {
        return sharedPreferences?.getString(key, defValue)
    }

    private fun setPrefLong(key: String, value: Long) {
        editor?.putLong(key, value)
        editor?.apply()
    }

    private fun getPrefLong(key: String, defValue: Long): Long? {
        return sharedPreferences?.getLong(key, defValue)
    }

    var prefToken: String?
        get() = getPrefString(PREF_TOKEN, "")
        set(token) {
            token?.let { setPrefString(PREF_TOKEN, it) }
        }

    var prefUsername: String?
        get() = getPrefString(PREF_USERNAME, "")
        set(token) {
            token?.let { setPrefString(PREF_USERNAME, it) }
        }

    var prefEmail: String?
        get() = getPrefString(PREF_EMAIL, "")
        set(token) {
            token?.let { setPrefString(PREF_EMAIL, it) }
        }

    var prefLastUpdateReport: Long?
        get() = getPrefLong(PREF_LAST_UPDATE_REPORT, 0)
        set(value) {
            value?.let { setPrefLong(PREF_LAST_UPDATE_REPORT, it) }
        }

    var prefLastUpdateFraud: Long?
        get() = getPrefLong(PREF_LAST_UPDATE_FRAUD, 0)
        set(value) {
            value?.let { setPrefLong(PREF_LAST_UPDATE_FRAUD, it) }
        }

    companion object {
        var instance: PreferencesManager? = null
            get() {
                val context: Context? = App.appContext
                if (field == null) {
                    field = PreferencesManager(context)
                }
                return field
            }
            private set
        private const val SHARE_PREFERENCES = "share_preference"
        private const val PREF_USERNAME = "pref_username"
        private const val PREF_TOKEN = "pref_token"
        private const val PREF_EMAIL = "pref_email"
        private const val PREF_LAST_UPDATE_REPORT = "pref_last_update_report"
        private const val PREF_LAST_UPDATE_FRAUD = "pref_last_update_fraud"
    }

    init {
        sharedPreferences = context?.getSharedPreferences(SHARE_PREFERENCES, 0)
        editor = sharedPreferences?.edit()
        editor?.apply()
    }
}