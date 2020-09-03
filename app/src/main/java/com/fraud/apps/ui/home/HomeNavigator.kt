package com.fraud.apps.ui.home

import com.fraud.apps.databinding.ItemReportBinding
import com.fraud.apps.domain.models.Report
import com.fraud.apps.ui.base.BaseNavigator

interface HomeNavigator : BaseNavigator {
    fun showError(message: String)
    fun movePage(report: Report, binding: ItemReportBinding)
    fun displayAddReportPage()

    interface CallbackFraud{
        fun updatePrice(value: Double)
    }
}
