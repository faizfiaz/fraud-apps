package com.fitness.tracking.ui.addFraud

import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.ui.base.BaseNavigator

interface AddFraudNavigator : BaseNavigator {
    fun successAddFraud(fraud: Fraud)
    fun successEditFraud()
}