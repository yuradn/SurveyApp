package com.xm.surveyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xm.surveyapp.ui.foundation.AppDestination
import com.xm.surveyapp.ui.screens.initial.InitialScreen
import com.xm.surveyapp.ui.screens.survey.SurveyMainScreen

@Composable
fun AppNavController(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AppDestination.InitialScreen.route) {
        composable(AppDestination.InitialScreen.route) {
            InitialScreen(navController)
        }
        composable(AppDestination.SurveyScreen.route) {
            SurveyMainScreen(navController)
        }
    }
}
