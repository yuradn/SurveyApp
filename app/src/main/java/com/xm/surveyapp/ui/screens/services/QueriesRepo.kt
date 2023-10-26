package com.xm.surveyapp.ui.screens.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.xm.surveyapp.repository.QueriesRepository
import com.xm.surveyapp.repository.QueriesRepositoryImpl

val LocalQueriesRepository = staticCompositionLocalOf<QueriesRepository> {
    error("No any QueriesRepository in composition")
}

@Composable
fun rememberQueriesRepository(): QueriesRepository {
    return QueriesRepositoryImpl()
}