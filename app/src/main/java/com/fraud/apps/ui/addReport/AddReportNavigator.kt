package com.fraud.apps.ui.addReport

import com.fraud.apps.domain.models.Report
import com.fraud.apps.ui.base.BaseNavigator

interface AddReportNavigator : BaseNavigator{
    fun successAddReport(report: Report)
}