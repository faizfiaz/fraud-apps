package com.fitness.tracking.di.builder

import com.fitness.tracking.ui.addFraud.AddFraudFragment
import com.fitness.tracking.ui.addReport.AddReportFragment
import com.fitness.tracking.ui.datePicker.DatePickerDialogFragment
import com.fitness.tracking.ui.fraud.FraudFragment
import com.fitness.tracking.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [])
    abstract fun bindHomeFragment(): HomeFragment?

    @ContributesAndroidInjector(modules = [])
    abstract fun bindFraudFragment(): FraudFragment?

    @ContributesAndroidInjector(modules = [])
    abstract fun bindAddReportFragment(): AddReportFragment?

    @ContributesAndroidInjector(modules = [])
    abstract fun bindAddFraudFragment(): AddFraudFragment?

    @ContributesAndroidInjector(modules = [])
    abstract fun bindDatePickerDialog(): DatePickerDialogFragment?

}