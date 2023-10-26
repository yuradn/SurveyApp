package com.xm.surveyapp.di

import com.xm.surveyapp.repository.AnswersRepository
import com.xm.surveyapp.repository.AnswersRepositoryImpl
import com.xm.surveyapp.repository.ApiRepository
import com.xm.surveyapp.repository.ApiRepositoryImpl
import com.xm.surveyapp.repository.QueriesRepository
import com.xm.surveyapp.repository.QueriesRepositoryImpl
import com.xm.surveyapp.ui.screens.survey.SurveyViewModel
import com.xm.surveyapp.ui.screens.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    factoryOf<ApiRepository>(::ApiRepositoryImpl)
    singleOf<QueriesRepository>(::QueriesRepositoryImpl)
    singleOf<AnswersRepository>(::AnswersRepositoryImpl)
}

val viewModelModule = module {
    viewModel { WelcomeViewModel(get(), get()) }
    viewModel { SurveyViewModel(get(), get()) }
}
