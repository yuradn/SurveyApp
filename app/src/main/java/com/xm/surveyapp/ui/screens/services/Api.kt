package com.xm.surveyapp.ui.screens.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.xm.surveyapp.repository.ApiRepository
import com.xm.surveyapp.repository.ApiRepositoryImpl

val LocalApiRepository = staticCompositionLocalOf<ApiRepository> {
    error("No any ApiRepository in composition")
}

@Composable
fun rememberApi(): ApiRepository {
    return ApiRepositoryImpl()
}