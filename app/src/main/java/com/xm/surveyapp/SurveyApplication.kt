package com.xm.surveyapp

import android.app.Application
import com.xm.surveyapp.di.appModule
import com.xm.surveyapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SurveyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SurveyApplication)
            modules(appModule, viewModelModule)
        }
    }
}