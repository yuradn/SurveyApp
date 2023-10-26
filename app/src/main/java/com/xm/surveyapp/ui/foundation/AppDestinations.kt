package com.xm.surveyapp.ui.foundation

sealed class AppDestination(val route: String) {
    object InitialScreen: AppDestination("initial")
    object SurveyScreen: AppDestination("survey")
}