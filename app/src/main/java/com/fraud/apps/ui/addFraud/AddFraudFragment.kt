package com.fraud.apps.ui.addFraud

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fraud.apps.BR
import com.fraud.apps.R
import com.fraud.apps.ViewModelProviderFactory
import com.fraud.apps.databinding.FragmentAddFraudBinding
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.ui.base.BaseFragment
import javax.inject.Inject

class AddFraudFragment : BaseFragment<FragmentAddFraudBinding, AddFraudViewModel>(),
        AddFraudNavigator {

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_add_fraud
    override val viewModel: AddFraudViewModel
        get() = ViewModelProvider(this, factory!!).get(AddFraudViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        viewModel.initData(arguments?.getString("reportId"),
                arguments?.getSerializable("data") as Fraud)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.activity = activity
        setListener()
    }

    private fun setListener() {
        viewModel.typeFraud.observe(viewLifecycleOwner, Observer { t ->
            if (t.isNullOrBlank() || t.length < 4) {
                viewDataBinding?.edtTypeFraud?.error = "Minimum 4 Character"
            } else {
                viewDataBinding?.edtTypeFraud?.error = null
            }
        })

        viewModel.totalLoss.observe(viewLifecycleOwner, Observer { t ->
            if (t.isNullOrBlank()) {
                viewDataBinding?.edtTotalLoss?.error = "Cannot zero or empty"
            } else {
                viewDataBinding?.edtTotalLoss?.error = null
            }
        })

        viewModel.city.observe(viewLifecycleOwner, Observer { t ->
            if (t.isNullOrBlank() || t.length < 4) {
                viewDataBinding?.edtCity?.error = "Minimum 4 Character"
            } else {
                viewDataBinding?.edtCity?.error = null
            }
        })
    }

    override fun successAddFraud(fraud: Fraud) {
        Toast.makeText(context, "Success Add Fraud", Toast.LENGTH_SHORT).show()
        findNavController().previousBackStackEntry?.savedStateHandle?.set("data", fraud)
        findNavController().popBackStack()
    }

    override fun successEditFraud() {
        Toast.makeText(context, "Success Edit Fraud", Toast.LENGTH_SHORT).show()
        findNavController().previousBackStackEntry?.savedStateHandle?.set("refresh", true)
        findNavController().popBackStack()
    }

    override fun handleError(throwable: Throwable?) {
        showToast(throwable?.message!!)
    }
}