package com.fraud.apps.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fraud.apps.databinding.ItemReportBinding
import com.fraud.apps.domain.models.Report
import com.fraud.apps.ui.base.BaseAdapter

class ReportListAdapter(data: ArrayList<Report>, action: (Report, ItemReportBinding) -> Unit) : BaseAdapter<Report>(data) {

    private val action: (Report, ItemReportBinding) -> Unit = action
    private lateinit var binding: ItemReportBinding


    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return GenericViewHolder(binding)
    }

    override fun bindingViewHolder(holder: GenericViewHolder, position: Int) {
        if (holder.viewDataBinding is ItemReportBinding) {
            (holder.viewDataBinding as ItemReportBinding).itemViewModel =
                    ReportListItemViewModel(getItem(position), action, binding)
        }
    }

}