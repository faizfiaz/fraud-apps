package com.fraud.apps

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.fraud.apps.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class App : MultiDexApplication(), HasActivityInjector, HasSupportFragmentInjector {

    companion object {
        var appContext: Context? = null
            private set
        var instance: App? = null
            private set
    }

    @JvmField
    @Inject
    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    @JvmField
    @Inject
    var mFragmentInjector: DispatchingAndroidInjector<Fragment>? = null

    @JvmField
    @Inject
    var mCalligraphyConfig: CalligraphyConfig? = null

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector!!
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mFragmentInjector!!
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        instance = this
        appContext = applicationContext
        DaggerAppComponent.builder()
                .application(this)
                ?.build()
                ?.inject(this)
        CalligraphyConfig.initDefault(mCalligraphyConfig)
    }

}