package com.fitness.tracking.ui.addReport

import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fitness.tracking.R
import com.fitness.tracking.domain.models.Report
import com.fitness.tracking.domain.usecases.user.IAppUsecases
import com.fitness.tracking.ui.base.BaseViewModel
import com.fitness.tracking.utils.AndroidUtils
import com.fitness.tracking.utils.SchedulerProvider
import com.fitness.tracking.utils.Validator
import kotlinx.coroutines.launch

open class AddReportViewModel(baseUsecase: IAppUsecases, schedulerProvider: SchedulerProvider)
    : BaseViewModel<IAppUsecases, AddReportNavigator>(baseUsecase, schedulerProvider) {

    var number = MutableLiveData<String>()
    var typeNumber = ObservableField<String>()

    fun afterNumberChanged(s: Editable) {
        if (Validator.isValidCharacterForNumber(s.toString())) {
            number.postValue(s.toString())
        } else {
            number.postValue(number.value)
        }
    }

    override fun defineLayout() {
        appBarTitle.set(AndroidUtils.getString(R.string.label_add_report))
    }

    fun createReport() {
        if (number.value != null && number.value!!.isNotEmpty()) {
            viewModelScope.launch {
                isLoading(true)
                try {
                    val responseApi = baseUsecase.addReport(
                            number.value!!,
                            typeNumber.get() == "Account Bank"
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
            navigator?.successAddReport(responseApi as Report)
        }
    }

    override fun onSuccess(o: Any?) {

    }
}