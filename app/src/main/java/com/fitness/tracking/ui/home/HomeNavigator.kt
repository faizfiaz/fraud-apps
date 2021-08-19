package com.fitness.tracking.ui.home

import com.fitness.tracking.databinding.ItemReportBinding
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.ui.base.BaseNavigator

interface HomeNavigator : BaseNavigator {
    fun showError(message: String)
    fun movePage(report: Report, binding: ItemReportBinding)
    fun displayAddReportPage()

    interface CallbackFraud{
        fun updatePrice(value: Double)
    }
}
