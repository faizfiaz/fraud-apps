package com.fitness.tracking.ui.datePicker

import com.fitness.tracking.ui.base.BaseNavigator

interface DatePickerNavigator : BaseNavigator {
    fun dismissDialog()
    fun showTimePicker()
    fun sendDateTime(dateTime: String?)
}