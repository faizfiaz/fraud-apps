package com.fitness.tracking.ui.datePicker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.fitness.tracking.BR
import com.fitness.tracking.R
import com.fitness.tracking.ViewModelProviderFactory
import com.fitness.tracking.databinding.DialogCalendarBinding
import com.fitness.tracking.ui.base.BaseDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

import javax.inject.Inject

class DatePickerDialogFragment : BaseDialog<CallbackDatePicker, DialogCalendarBinding, DatePickerViewModel>(), DatePickerNavigator, OnDateSelectedListener {

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null

    companion object {
        var TITLE = "TITLE"
        var TITLE_HOUR = "TITLE_HOUR"
        var TITLE_BUTTON = "TITLE_BUTTON"
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.dialog_calendar
    override val viewModel: DatePickerViewModel
        get() = ViewModelProvider(this, factory!!).get(DatePickerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        viewModel.initData(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.datePicker?.setOnDateChangedListener(this)
    }

    override fun dismissDialog() {
        super.dismiss()
    }

    override fun showTimePicker() {
        SnapTimePickerDialog.Builder().apply {
            setTitle(R.string.arrive_time)
            setThemeColor(R.color.colorPrimary)
            setTitleColor(R.color.white)
        }.build().apply {
            setListener { hour, minute ->
                viewModel.setTime(hour, minute)
            }
        }.show(childFragmentManager, tag)
    }

    override fun sendDateTime(dateTime: String?) {
        callback?.onPick(dateTime!!)
        dismissDialog()
    }

    override fun handleError(throwable: Throwable?) {
        Toast.makeText(context, throwable?.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        viewModel.setDate(date)
    }


}