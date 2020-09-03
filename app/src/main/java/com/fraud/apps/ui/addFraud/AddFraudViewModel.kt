package com.fraud.apps.ui.addFraud

import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fraud.apps.R
import com.fraud.apps.domain.models.Fraud
import com.fraud.apps.domain.usecases.user.IAppUsecases
import com.fraud.apps.ui.base.BaseViewModel
import com.fraud.apps.utils.AndroidUtils
import com.fraud.apps.utils.SchedulerProvider
import kotlinx.coroutines.launch

open class AddFraudViewModel(baseUsecase: IAppUsecases, schedulerProvider: SchedulerProvider)
    : BaseViewModel<IAppUsecases, AddFraudNavigator>(baseUsecase, schedulerProvider) {

    var typeFraud = MutableLiveData<String>()
    var totalLoss = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var buttonFraud = ObservableField<String>()

    var reportId = ""
    var fraudId = ""

    var isEdit = false
    lateinit var fraud: Fraud
    fun initData(reportId: String?, fraud: Fraud) {
        if (reportId != null) {
            this.reportId = reportId
            isEdit = false
            buttonFraud.set("Submit Fraud")
        } else {
            this.fraud = fraud
            isEdit = true
            fraudId = fraud.id!!
            this.reportId = fraud.reportId!!
            typeFraud.postValue(fraud.fraudType)
            totalLoss.postValue(fraud.totalLoss.toString())
            city.postValue(fraud.cityVictim)
            buttonFraud.set("Edit Fraud")
        }
    }


    fun afterTypeFraudChanged(s: Editable) {
        typeFraud.postValue(s.toString())
    }

    fun afterTotalLossChanged(s: Editable) {
        totalLoss.postValue(s.toString())
    }

    fun afterCityChanged(s: Editable) {
        city.postValue(s.toString())
    }


    override fun defineLayout() {
        appBarTitle.set(AndroidUtils.getString(R.string.label_add_report))
    }

    fun createFraud() {
        if (typeFraud.value != null && typeFraud.value!!.isNotEmpty() &&
                totalLoss.value != null && totalLoss.value!!.isNotEmpty() &&
                city.value != null && city.value!!.isNotEmpty()) {
            viewModelScope.launch {
                isLoading(true)
                try {
                    val responseApi = baseUsecase.addFraud(
                            reportId,
                            typeFraud.value!!,
                            totalLoss.value!!,
                            city.value!!,
                            isEdit,
                            fraudId
                    )
                    checkResponse(responseApi.blockingGet())
                } catch (e: Exception) {
                    onError(e)
                }
            }
        } else {
            onError(Throwable("All Field must be filled"))
        }
    }

    private fun checkResponse(responseApi: Any?) {
        isLoading(false)
        if (responseApi is Boolean) {
            navigator?.handleError(Throwable("Something wrong"))
        } else {
            if (isEdit) {
                navigator?.successEditFraud()
            } else {
                navigator?.successAddFraud(responseApi as Fraud)
            }
        }
    }

    override fun onSuccess(o: Any?) {

    }
}