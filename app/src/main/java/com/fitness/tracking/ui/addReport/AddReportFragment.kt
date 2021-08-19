package com.fitness.tracking.ui.addReport

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fitness.tracking.BR
import com.fitness.tracking.R
import com.fitness.tracking.ViewModelProviderFactory
import com.fitness.tracking.databinding.FragmentAddReportBinding
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.ui.base.BaseFragment
import javax.inject.Inject

class AddReportFragment : BaseFragment<FragmentAddReportBinding, AddReportViewModel>(),
        AddReportNavigator {

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_add_report
    override val viewModel: AddReportViewModel
        get() = ViewModelProvider(this, factory!!).get(AddReportViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.activity = activity
        setListener()
    }

    private fun setListener() {
        viewModel.number.observe(viewLifecycleOwner, Observer { t ->
            if (t.isNullOrBlank() || t.length < 4) {
                viewDataBinding?.edtNumber?.error = "Minimum 4 Character"
            } else {
                viewDataBinding?.edtNumber?.error = null
            }
            if (viewModel.number.value != null && viewModel.number.value!!.isNotEmpty()) {
                viewDataBinding?.edtNumber?.setSelection(viewModel.number.value!!.length)
            }

        })
    }

    override fun successAddReport(report: Report) {
        Toast.makeText(context, "Success Add Report", Toast.LENGTH_SHORT).show()
        findNavController().previousBackStackEntry?.savedStateHandle?.set("data", report)
        findNavController().popBackStack()
    }

    override fun handleError(throwable: Throwable?) {
        showToast(throwable?.message!!)
    }
}