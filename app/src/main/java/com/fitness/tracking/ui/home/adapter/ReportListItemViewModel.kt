package com.fitness.tracking.ui.home.adapter


import androidx.core.view.ViewCompat
import androidx.databinding.ObservableField
import com.fitness.tracking.databinding.ItemReportBinding
import com.fitness.tracking.domain.models.Report
import java.util.*

class ReportListItemViewModel(itemData: Report?, var actionDetail: (Report, ItemReportBinding) -> Unit,
                              var binding: ItemReportBinding) : Observable() {

    val number = ObservableField<String>()

    var data: Report? = itemData

    init {
        number.set(data?.number)
    }

    fun goDetail() {
        ViewCompat.setTransitionName(binding.tvNumber, "number" + data?.id)
        with(binding) {
            executePendingBindings()
        }
        actionDetail(data!!, binding)
    }

}