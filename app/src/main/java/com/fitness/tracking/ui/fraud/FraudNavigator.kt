package com.fitness.tracking.ui.fraud

import com.fitness.tracking.databinding.ItemFraudBinding
import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.ui.base.BaseNavigator

interface FraudNavigator : BaseNavigator {
    fun showAddFraudPage(id: String)
    fun showEditeFraudPage(fraud: Fraud, itemFraudBinding: ItemFraudBinding)
}