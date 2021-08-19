package com.fitness.tracking.ui.addReport

import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.ui.base.BaseNavigator

interface AddReportNavigator : BaseNavigator{
    fun successAddReport(report: Report)
}