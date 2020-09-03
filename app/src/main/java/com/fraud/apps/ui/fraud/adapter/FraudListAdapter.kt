package com.fraud.apps.ui.fraud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fraud.apps.databinding.ItemFraudBinding
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.ui.base.BaseAdapter

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