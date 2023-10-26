package com.xm.surveyapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.xm.surveyapp.ui.navigation.AppNavController
import com.xm.surveyapp.services.LocalApiRepository
import com.xm.surveyapp.services.LocalQueriesRepository
import com.xm.surveyapp.services.rememberApi
import com.xm.surveyapp.services.rememberQueriesRepository
import com.xm.surveyapp.ui.theme.SurveyAppTheme

@Composable
fun UI() {
    SurveyAppTheme {
        CompositionLocalProvider(
            LocalApiRepository provides rememberApi(),
            LocalQueriesRepository provides rememberQueriesRepository()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavController()
            }
        }
    }

}