package com.fitness.tracking.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.fitness.tracking.BR
import com.fitness.tracking.R
import com.fitness.tracking.ViewModelProviderFactory
import com.fitness.tracking.databinding.FragmentHomeBinding
import com.fitness.tracking.databinding.ItemReportBinding
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.ui.base.BaseFragment
import javax.inject.Inject


class HomeFragment : BaseFragment<FragmentHomeBinding?, HomeViewModel>(), HomeNavigator {

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel
        get() = ViewModelProvider(this, factory!!).get(HomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            viewModel.doGetReports()
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Report>("data")?.observe(viewLifecycleOwner)
        { data ->
            if (data != null) {
                viewModel.doGetReports()
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("needRefresh")?.observe(viewLifecycleOwner)
        { needRefresh ->
            if (needRefresh != null && needRefresh) {
                viewModel.getFraudData()
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun movePage(report: Report, binding: ItemReportBinding) {
        var bundle = Bundle()
        bundle.putSerializable("data", report)
        val extras = FragmentNavigatorExtras(
                binding.tvNumber to "number" + report.id
        )
        findNavController().navigate(R.id.action_homeFragment_to_fraudFragment, bundle, null, extras)
    }

    override fun displayAddReportPage() {
        findNavController().navigate(R.id.action_homeFragment_to_addReportFragment)
    }

    override fun handleError(throwable: Throwable?) {
        Toast.makeText(context, throwable?.message, Toast.LENGTH_SHORT).show()
    }
}