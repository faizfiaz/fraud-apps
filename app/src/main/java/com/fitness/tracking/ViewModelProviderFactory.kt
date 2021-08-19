package com.fitness.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.fitness.tracking.data.remote.AppRepository
import com.fitness.tracking.domain.mappers.FraudMapper
import com.fitness.tracking.domain.mappers.ReportMapper
import com.fitness.tracking.domain.usecases.user.AppUsecases
import com.fitness.tracking.domain.usecases.user.IAppUsecases
import com.fitness.tracking.ui.addFraud.AddFraudViewModel
import com.fitness.tracking.ui.addReport.AddReportViewModel
import com.fitness.tracking.ui.datePicker.DatePickerViewModel
import com.fitness.tracking.ui.fraud.FraudViewModel
import com.fitness.tracking.ui.home.HomeViewModel
import com.fitness.tracking.utils.SchedulerProvider
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