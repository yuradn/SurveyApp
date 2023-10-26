package com.xm.surveyapp.ui.navigation

sealed class AppDestination(val route: String) {
    object InitialScreen: AppDestination("initial")
    object SurveyScreen: AppDestination("survey")
}