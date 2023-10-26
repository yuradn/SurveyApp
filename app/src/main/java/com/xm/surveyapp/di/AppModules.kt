package com.xm.surveyapp.di

import com.xm.surveyapp.repository.ApiRepository
import com.xm.surveyapp.repository.ApiRepositoryImpl
import com.xm.surveyapp.repository.QueriesRepository
import com.xm.surveyapp.repository.QueriesRepositoryImpl
import com.xm.surveyapp.ui.screens.initial.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf<ApiRepository>(::ApiRepositoryImpl)
    singleOf<QueriesRepository>(::QueriesRepositoryImpl)
}

val viewModelModule = module {
    viewModel { WelcomeViewModel(get(), get()) }
}
