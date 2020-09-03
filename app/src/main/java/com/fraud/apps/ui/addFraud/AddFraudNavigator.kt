package com.fraud.apps.ui.addFraud

import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.ui.base.BaseNavigator

interface AddFraudNavigator : BaseNavigator {
    fun successAddFraud(fraud: Fraud)
    fun successEditFraud()
}