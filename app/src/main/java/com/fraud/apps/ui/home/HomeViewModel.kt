@file:Suppress("UNCHECKED_CAST")

package com.fraud.apps.ui.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.fraud.apps.R
import com.fraud.apps.databinding.ItemReportBinding
import com.fraud.apps.domain.models.Report
import com.fraud.apps.domain.usecases.user.IAppUsecases
import com.fraud.apps.ui.base.BaseViewModel
import com.fraud.apps.ui.home.adapter.ReportListAdapter
import com.fraud.apps.utils.AndroidUtils
import com.fraud.apps.utils.FormatterDecimal
import com.fraud.apps.utils.SchedulerProvider
import io.reactivex.Single
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.math.ceil

open class HomeViewModel(baseUsecase: IAppUsecases, schedulerProvider: SchedulerProvider)
    : BaseViewModel<IAppUsecases, HomeNavigator>(baseUsecase, schedulerProvider),
        HomeNavigator.CallbackFraud {

    var valueTotalAccount = ObservableField<String>("0")
    var valueTotalPhoneNumber = ObservableField<String>("0")
    var valueTotalLoss = ObservableField<String>("Rp0")
    var valueNewLoss = ObservableField<String>("Rp0")

    var pageInfo = ObservableField<String>("Page 0/0")
    var isEmpty = ObservableBoolean(true)

    private var reportListAdapter = ReportListAdapter(ArrayList(), ::goToDetailReport)
    private var page = 0
    private var totalItems = 0
    private var totalPage: Double = 0.0

    override fun defineLayout() {
        appBarTitle.set(AndroidUtils.getString(R.string.app_name))
    }

    fun getAdapter(): ReportListAdapter {
        return reportListAdapter
    }

    private fun goToDetailReport(report: Report, binding: ItemReportBinding) {
        navigator?.movePage(report, binding)
    }

    fun doGetReports() {
        viewModelScope.launch {
            isLoading(true)
            try {
                val responseApi = baseUsecase.getReportList()
                checkResponse((responseApi as Single<*>).blockingGet())
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun checkResponse(responseApi: Any?) {
        isLoading(false)
        when (responseApi) {
            is String -> {
                navigator?.showError(responseApi.toString())
            }
            is Boolean -> {
                loadDataLocal()
                checkPageLocal()
                getFraudData()
                getTotalAccountAndPhone()
            }
            else -> {
                saveData(responseApi as List<Report>)
            }
        }
    }

    private fun getTotalAccountAndPhone() {
        viewModelScope.launch {
            try {
                val countItems = async { baseUsecase.getCountAccountOrPhone() }
                val totalAccount = countItems.await()
                valueTotalAccount.set("$totalAccount+")
                valueTotalPhoneNumber.set("${totalItems - totalAccount}+")
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun checkPageLocal() {
        viewModelScope.launch {
            try {
                val countItems = async { baseUsecase.getTotalItems() }
                totalItems = countItems.await()
                checkPage()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun saveData(list: List<Report>) {
        viewModelScope.launch {
            try {
                val responseSave = async { baseUsecase.saveData(list) }
                if (responseSave.await() == list.size.toLong()) {
                    totalItems = list.size
                    checkPage()
                }
                getFraudData()
                loadDataLocal()
                getTotalAccountAndPhone()

            } catch (e: Exception) {
                onError(e)
            }
        }

    }

    private fun loadDataLocal() {
        viewModelScope.launch {
            try {
                val getData = baseUsecase.getReportLocal(page)
                populateData(getData as List<Report>)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun populateData(list: List<Report>) {
        isEmpty.set(list.isEmpty())
        reportListAdapter.clearItems()
        reportListAdapter.addItems(list)
    }

    fun getFraudData() {
        viewModelScope.launch {
            baseUsecase.getAllFraudList(this@HomeViewModel)
            val latestFraud = baseUsecase.getLatestFraud()
            valueNewLoss.set("Rp${FormatterDecimal.decimalFormat(latestFraud)}")
        }
    }


    override fun onSuccess(o: Any?) {

    }

    fun clickAddReport() {
        navigator?.displayAddReportPage()
    }

    fun addData(data: Report?) {
        reportListAdapter.addItem(data!!)
    }

    fun checkPage(): Boolean {
        totalPage = ceil(totalItems.toDouble() / 10)
        updatePageInfo()
        return page >= totalPage
    }

    fun showPreviousPage() {
        if (page != 0) {
            viewModelScope.launch {
                try {
                    val getData = baseUsecase.getReportLocal(--page)
                    populateData(getData as List<Report>)
                    updatePageInfo()
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }

    private fun updatePageInfo() {
        pageInfo.set("Page ${page + 1} / ${totalPage.toInt()}")
    }

    fun showNextPage() {
        if (page < totalPage - 1) {
            viewModelScope.launch {
                try {
                    val getData = baseUsecase.getReportLocal(++page)
                    populateData(getData as List<Report>)
                    updatePageInfo()
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }

    override fun updatePrice(value: Double) {
        val total = BigDecimal.valueOf(value)
        valueTotalLoss.set(FormatterDecimal.convertBigDecimalDisplay(total))
    }


}