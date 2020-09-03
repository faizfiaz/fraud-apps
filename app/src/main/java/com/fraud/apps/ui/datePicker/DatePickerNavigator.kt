package com.fraud.apps.ui.datePicker

import com.fraud.apps.ui.base.BaseNavigator

interface DatePickerNavigator : BaseNavigator {
    fun dismissDialog()
    fun showTimePicker()
    fun sendDateTime(dateTime: String?)
}