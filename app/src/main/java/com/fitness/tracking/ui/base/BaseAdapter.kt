package com.fitness.tracking.ui.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<M>(dataList: ArrayList<M>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listData = mutableListOf<M>().apply { addAll(dataList) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createHolder(parent, viewType)
    }

    abstract fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        bindingViewHolder(holder as BaseAdapter<M>.GenericViewHolder, position)
        (holder as BaseAdapter<*>.GenericViewHolder).bind()
    }

    fun getItem(position: Int): M? {
        return listData[position]
    }

    fun addItems(datas: List<M>) {
        var lastIndex = listData.size
        listData.addAll(datas)
        notifyItemRangeInserted(lastIndex, listData.size)
    }

    fun addItem(data: M) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    @SuppressLint("CheckResult")
    fun clearLatest() {
        val totalItem = itemCount
        listData.removeAt(totalItem - 1)
        notifyItemRemoved(totalItem - 1)
    }


    @SuppressLint("CheckResult")
    fun clearItems() {
        val totalItem = itemCount
        listData.clear()
        notifyItemRangeRemoved(0, totalItem)
    }

    abstract fun bindingViewHolder(holder: GenericViewHolder, position: Int)

    inner class GenericViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
            LifecycleOwner {

        private val lifecycleRegistry = LifecycleRegistry(this)
        private var wasPaused: Boolean = false

        var viewDataBinding = binding

        fun bind() {
            binding.executePendingBindings()
            binding.lifecycleOwner = this
        }

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        fun markCreated() {
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }

        fun markAttach() {
            if (wasPaused) {
                lifecycleRegistry.currentState = Lifecycle.State.RESUMED
                wasPaused = false
            } else {
                lifecycleRegistry.currentState = Lifecycle.State.STARTED
            }
        }

        fun markDetach() {
            wasPaused = true
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }

        fun markDestroyed() {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

    }
}