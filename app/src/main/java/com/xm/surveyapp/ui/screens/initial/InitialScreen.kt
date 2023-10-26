package com.xm.surveyapp.ui.screens.initial

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.xm.surveyapp.repository.QueriesRepository
import com.xm.surveyapp.ui.foundation.AppDestination
import com.xm.surveyapp.ui.screens.services.LocalQueriesRepository

@Composable
fun InitialScreen(navController: NavHostController) {
    val queriesRepository: QueriesRepository = LocalQueriesRepository.current
    WelcomeScreen(action = {
        queriesRepository.setQuery(it)
        navController.navigate(AppDestination.SurveyScreen.route)
    })
}