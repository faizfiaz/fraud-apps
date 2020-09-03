package com.fraud.apps.di.component

import android.app.Application
import com.fraud.apps.App
import com.fraud.apps.di.builder.ActivityBuilder
import com.fraud.apps.di.builder.FragmentBuilder
import com.fraud.apps.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class, FragmentBuilder::class])
interface AppComponent {
    fun inject(app: App?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?

        fun build(): AppComponent?
    }
}