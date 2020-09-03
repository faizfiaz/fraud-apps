package com.fraud.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.fraud.apps.data.remote.AppRepository
import com.fraud.apps.domain.mappers.FraudMapper
import com.fraud.apps.domain.mappers.ReportMapper
import com.fraud.apps.domain.usecases.user.AppUsecases
import com.fraud.apps.domain.usecases.user.IAppUsecases
import com.fraud.apps.ui.addFraud.AddFraudViewModel
import com.fraud.apps.ui.addReport.AddReportViewModel
import com.fraud.apps.ui.datePicker.DatePickerViewModel
import com.fraud.apps.ui.fraud.FraudViewModel
import com.fraud.apps.ui.home.HomeViewModel
import com.fraud.apps.utils.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory @Inject constructor(private val schedulerProvider: SchedulerProvider) : NewInstanceFactory() {
    private val appUsecases: IAppUsecases

    init {
        appUsecases = AppUsecases(ReportMapper(), FraudMapper(), AppRepository.instance!!)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(appUsecases, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(FraudViewModel::class.java) -> {
                FraudViewModel(appUsecases, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(AddReportViewModel::class.java) -> {
                AddReportViewModel(appUsecases, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(AddFraudViewModel::class.java) -> {
                AddFraudViewModel(appUsecases, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(DatePickerViewModel::class.java) -> {
                DatePickerViewModel(appUsecases, schedulerProvider) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }


}