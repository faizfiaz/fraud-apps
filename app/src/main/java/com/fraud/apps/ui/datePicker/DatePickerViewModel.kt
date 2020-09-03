package com.fraud.apps.ui.datePicker

import android.os.Bundle
import androidx.databinding.ObservableField
import com.fraud.apps.R
import com.fraud.apps.ui.base.BaseViewModel
import com.fraud.apps.utils.AndroidUtils
import com.fraud.apps.utils.FormatterDate
import com.fraud.apps.utils.SchedulerProvider
import com.prolificinteractive.materialcalendarview.CalendarDay

class DatePickerViewModel(baseUsecase: Any?, schedulerProvider: SchedulerProvider)
    : BaseViewModel<Any?, DatePickerNavigator>(baseUsecase, schedulerProvider) {

    var title = ObservableField<String>(AndroidUtils.getString(R.string.arrive_date))
    var titleHour = ObservableField<String>(AndroidUtils.getString(R.string.arrive_time))
    var titleButton = ObservableField<String>(AndroidUtils.getString(R.string.label_eta))

    var selectedDateTime = ObservableField<String>("")
    var selectedDate = ObservableField<String>("")
    var selectedTime = ObservableField<String>("")

    override fun defineLayout() {
    }

    override fun onSuccess(o: Any?) {
    }

    fun dismiss() {
        navigator?.dismissDialog()
    }

    fun showTimePicker() {
        navigator?.showTimePicker()
    }

    fun initData(arguments: Bundle?) {
        title.set(arguments?.getString(DatePickerDialogFragment.TITLE))
        titleHour.set(arguments?.getString(DatePickerDialogFragment.TITLE_HOUR))
        titleButton.set(arguments?.getString(DatePickerDialogFragment.TITLE_BUTTON))
    }

    fun setDate(date: CalendarDay) {
        selectedDate.set(FormatterDate.formatDate(date.date.time))
        addDateTime()
    }

    private fun addDateTime() {
        selectedDateTime.set(String.format("%s %s", selectedDate.get(), selectedTime.get()))
    }

    fun setTime(hour: Int, minute: Int) {
        selectedTime.set(String.format("%02d:%02d:00", hour, minute))
        addDateTime()
    }

    fun pickDateTime() {
        if (validation()) {
            navigator?.sendDateTime(selectedDateTime.get())
        }else{
            navigator?.handleError(Throwable("Date and Time need to be filled"))
        }
    }

    private fun validation(): Boolean {
        return !selectedDate.get().isNullOrBlank() && !selectedTime.get().isNullOrBlank()
    }
}