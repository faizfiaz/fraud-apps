package com.fitness.tracking.di.module

import android.app.Application
import android.content.Context
import com.fitness.tracking.R
import com.fitness.tracking.utils.AndroidUtils
import com.fitness.tracking.utils.AppSchedulerProvider
import com.fitness.tracking.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
                .setDefaultFontPath(AndroidUtils.getString(R.string.font_path_poppins))
                .setFontAttrId(R.attr.fontPath)
                .build()
    }
}