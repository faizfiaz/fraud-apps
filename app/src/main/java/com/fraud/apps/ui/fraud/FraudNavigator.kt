package com.fraud.apps.ui.fraud

import com.fraud.apps.databinding.ItemFraudBinding
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.ui.base.BaseNavigator

interface FraudNavigator : BaseNavigator {
    fun showAddFraudPage(id: String)
    fun showEditeFraudPage(fraud: Fraud, itemFraudBinding: ItemFraudBinding)
}