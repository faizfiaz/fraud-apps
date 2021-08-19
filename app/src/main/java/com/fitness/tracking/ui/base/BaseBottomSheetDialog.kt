package com.fitness.tracking.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fitness.tracking.R
import com.fitness.tracking.ui.components.loadingDialog.DialogLoading
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseBottomSheetDialog<CB, VB : ViewDataBinding?, V : BaseViewModel<*, *>?> : BottomSheetDialogFragment() {

    private var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    var viewDataBinding: VB? = null
        private set
    private var mViewModel: V? = null
    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    var dialogLoading: DialogLoading? = null

    fun setCallback(callback: CB): BaseBottomSheetDialog<CB, VB, V> {
        this.callback = callback
        return this
    }

    protected var callback: CB? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity<*, *>) {
            val activity = context
            baseActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        mViewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<VB>(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }
}