package com.xm.surveyapp.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.xm.surveyapp.repository.QueriesRepository
import org.koin.compose.koinInject

val LocalQueriesRepository = staticCompositionLocalOf<QueriesRepository> {
    error("No any QueriesRepository in composition")
}

@Composable
fun rememberQueriesRepository(): QueriesRepository {
    return koinInject<QueriesRepository>()
}