package com.fraud.apps.di.builder

import com.fraud.apps.ui.addFraud.AddFraudFragment
import com.fraud.apps.ui.addReport.AddReportFragment
import com.fraud.apps.ui.datePicker.DatePickerDialogFragment
import com.fraud.apps.ui.fraud.FraudFragment
import com.fraud.apps.ui.home.HomeFragment
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