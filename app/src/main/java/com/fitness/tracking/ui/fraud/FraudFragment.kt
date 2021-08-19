package com.fitness.tracking.ui.fraud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.fitness.tracking.BR
import com.fitness.tracking.R
import com.fitness.tracking.ViewModelProviderFactory
import com.fitness.tracking.databinding.FragmentFraudBinding
import com.fitness.tracking.databinding.ItemFraudBinding
import com.fitness.tracking.domain.models.Fraud
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.ui.base.BaseFragment
import javax.inject.Inject


class FraudFragment : BaseFragment<FragmentFraudBinding?, FraudViewModel>(), FraudNavigator {

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_fraud
    override val viewModel: FraudViewModel
        get() = ViewModelProvider(this, factory!!).get(FraudViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        viewModel.initData(arguments?.get("data") as Report?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("needRefresh", viewModel.needRefreshHomePage)
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.activity = activity
        setupSharedElement()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Fraud>("data")?.observe(viewLifecycleOwner)
        { data ->
            if (data != null) {
                viewModel.addFraud(data)
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("refresh")?.observe(viewLifecycleOwner)
        { needRefresh ->
            if (needRefresh != null && needRefresh) {
                viewModel.refreshItemList()
            }
        }
    }

    private fun setupSharedElement() {
        setSharedElement(viewDataBinding?.tvTitle!!, "number" + viewModel.data.get()?.id)
    }

    override fun showAddFraudPage(id: String) {
        var bundle = Bundle()
        bundle.putString("reportId", id)
        findNavController().navigate(R.id.action_fraudFragment_to_addFraudFragment, bundle)
    }

    override fun showEditeFraudPage(fraud: Fraud, itemFraudBinding: ItemFraudBinding) {
        var bundle = Bundle()
        bundle.putSerializable("data", fraud)
        val extras = FragmentNavigatorExtras(
                itemFraudBinding.tvTypeFraud to "typeFraud" + fraud.id,
                itemFraudBinding.tvTotalLoss to "totalLoss" + fraud.id,
                itemFraudBinding.tvCity to "tvCity" + fraud.id
        )
        findNavController().navigate(R.id.action_fraudFragment_to_addFraudFragment, bundle, null, extras)
    }

    override fun handleError(throwable: Throwable?) {
        Toast.makeText(context, throwable?.message, Toast.LENGTH_SHORT).show()
    }
}