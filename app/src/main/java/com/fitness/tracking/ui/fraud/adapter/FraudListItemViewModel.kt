package com.fitness.tracking.ui.fraud.adapter


import androidx.core.view.ViewCompat
import androidx.databinding.ObservableField
import com.fitness.tracking.databinding.ItemFraudBinding
import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.utils.FormatterDecimal
import java.util.*

class FraudListItemViewModel(itemData: Fraud?, var actionDetail: (Fraud, ItemFraudBinding) -> Unit,
                             var binding: ItemFraudBinding) : Observable() {

    val typeFraud = ObservableField<String>()
    val totalLoss = ObservableField<String>()
    val city = ObservableField<String>()
    val lastUpdate = ObservableField<String>()

    var data: Fraud? = itemData

    init {
        typeFraud.set(data?.fraudType)
        totalLoss.set("Rp${FormatterDecimal.decimalFormat(data?.totalLoss)}")
        city.set(data?.cityVictim)
        lastUpdate.set(data?.updatedAt)
    }

    fun goDetail() {
        ViewCompat.setTransitionName(binding.tvTypeFraud, "typeFraud" + data?.id)
        ViewCompat.setTransitionName(binding.tvTotalLoss, "totalLoss" + data?.id)
        ViewCompat.setTransitionName(binding.tvCity, "tvCity" + data?.id)
        with(binding) {
            executePendingBindings()
        }
        actionDetail(data!!, binding)
    }

}