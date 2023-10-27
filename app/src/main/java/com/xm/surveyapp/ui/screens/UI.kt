package com.xm.surveyapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xm.surveyapp.ui.navigation.AppNavController
import com.xm.surveyapp.ui.theme.SurveyAppTheme

@Composable
fun UI() {
    SurveyAppTheme {
        Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavController()
            }
    }

}