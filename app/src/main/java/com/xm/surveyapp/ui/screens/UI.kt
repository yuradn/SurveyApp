@file:OptIn(ExperimentalMaterial3Api::class)

package com.xm.surveyapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.xm.surveyapp.ui.navigation.AppNavController
import com.xm.surveyapp.ui.screens.services.LocalApiRepository
import com.xm.surveyapp.ui.screens.services.LocalQueriesRepository
import com.xm.surveyapp.ui.screens.services.rememberApi
import com.xm.surveyapp.ui.screens.services.rememberQueriesRepository
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appbar(
    navController: NavHostController,
    page: Int? = null,
    number: Int? = null,
    actionPrev: () -> Unit,
    actionNext: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if (number != null) {
                Text("Question ${page?.plus(1)}/$number")
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },

        actions = {
            if (page != null && number != null) {
                TextButton(onClick = actionPrev) {
                    Text(text = "Previous", color = if (page > 0) Color.Blue else Color.Gray)
                }
                TextButton(onClick = actionNext) {
                    Text(text = "Next", color = if (page < number) Color.Blue else Color.Gray)
                }
            }
        }
    )
}