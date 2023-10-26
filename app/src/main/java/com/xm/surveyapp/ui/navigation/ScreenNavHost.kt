package com.xm.surveyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xm.surveyapp.ui.screens.welcome.WelcomeScreen
import com.xm.surveyapp.ui.screens.survey.SurveyScreen

@Composable
fun AppNavController(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AppDestination.InitialScreen.route) {
        composable(AppDestination.InitialScreen.route) {
            WelcomeScreen(navController)
        }
        composable(AppDestination.SurveyScreen.route) {
            SurveyScreen(navController)
        }
    }
}
