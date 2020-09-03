package com.fraud.apps.ui.fraud

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.fraud.apps.R
import com.fraud.apps.databinding.ItemFraudBinding
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.domain.models.Report
import com.fraud.apps.domain.usecases.user.IAppUsecases
import com.fraud.apps.ui.base.BaseViewModel
import com.fraud.apps.ui.fraud.adapter.FraudListAdapter
import com.fraud.apps.utils.AndroidUtils
import com.fraud.apps.utils.SchedulerProvider
import kotlinx.coroutines.launch

open class FraudViewModel(baseUsecase: IAppUsecases, schedulerProvider: SchedulerProvider)
    : BaseViewModel<IAppUsecases, FraudNavigator>(baseUsecase, schedulerProvider) {

    var data = ObservableField<Report>()
    var number = ObservableField<String>()

    var needRefreshHomePage = false

    private var fraudListAdapter = FraudListAdapter(ArrayList(), ::goToDetailFraud)

    private fun goToDetailFraud(fraud: Fraud, itemFraudBinding: ItemFraudBinding) {
        navigator?.showEditeFraudPage(fraud, itemFraudBinding)
    }

    override fun defineLayout() {
        appBarTitle.set(AndroidUtils.getString(R.string.label_detail_report))
    }

    fun getAdapter(): FraudListAdapter {
        return fraudListAdapter
    }

    override fun onSuccess(o: Any?) {

    }

    fun initData(data: Report?) {
        this.data.set(data)
        number.set(data?.number)
        getDataFraud()
    }

    private fun getDataFraud() {
        viewModelScope.launch {
            val fraudList = baseUsecase.getFraudById(data.get()?.id!!)
            fraudListAdapter.addItems(fraudList as List<Fraud>)
        }
    }

    fun clickAddFraud() {
        navigator?.showAddFraudPage(data.get()?.id!!)
    }

    fun addFraud(data: Fraud?) {
        needRefreshHomePage = true
        fraudListAdapter.addItem(data!!)
    }

    fun refreshItemList() {
        needRefreshHomePage = true
        fraudListAdapter.clearItems()
        getDataFraud()
    }


}