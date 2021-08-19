package com.fitness.tracking.ui.fraud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitness.tracking.databinding.ItemFraudBinding
import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.ui.base.BaseAdapter

class FraudListAdapter(data: ArrayList<Fraud>, action: (Fraud, ItemFraudBinding) -> Unit) : BaseAdapter<Fraud>(data) {

    private val action: (Fraud, ItemFraudBinding) -> Unit = action
    private lateinit var binding: ItemFraudBinding


    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemFraudBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return GenericViewHolder(binding)
    }

    override fun bindingViewHolder(holder: GenericViewHolder, position: Int) {
        if (holder.viewDataBinding is ItemFraudBinding) {
            (holder.viewDataBinding as ItemFraudBinding).itemViewModel =
                    FraudListItemViewModel(getItem(position), action, binding)
        }
    }

}