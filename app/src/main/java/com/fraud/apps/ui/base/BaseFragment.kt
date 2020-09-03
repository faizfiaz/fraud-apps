/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.fraud.apps.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.fraud.apps.ui.components.loadingDialog.DialogLoading
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<VB : ViewDataBinding?, V : BaseViewModel<*, *>?> : Fragment() {
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

    var currentScroll = 0

    var hasInitializedRootView = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity<*, *>) {
            val activity = context
            baseActivity = activity
            activity.onFragmentAttached()
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
        dialogLoading = DialogLoading(context!!)
        mViewModel?.showProgressBar?.observe(this, Observer<Boolean> { t ->
            if (t!!) {
                dialogLoading?.show()
            } else {
                dialogLoading?.dismiss()
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
        currentScroll = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<VB>(inflater, layoutId, container, false)
        if (mRootView == null) {
            mRootView = viewDataBinding!!.root
        } else {
            (mRootView?.parent as? ViewGroup)?.removeView(mRootView)
        }
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


    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }

    fun setSharedElement(view: View, tag: String) {
        ViewCompat.setTransitionName(view, tag)
    }

    fun <T : Any> Fragment.setBackStackData(key: String, data: T, doBack: Boolean = true) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
        if (doBack)
            findNavController().popBackStack()
    }

    fun <T : Any> Fragment.getBackStackData(key: String, result: (T) -> (Unit)) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
            result(it)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}