package com.xm.surveyapp.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.xm.surveyapp.ui.navigation.AppDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(navController: NavHostController, viewModel: WelcomeViewModel = koinViewModel()) {
    val screenState by viewModel.uiState.collectAsState()

    when (screenState) {
        is CompleteState -> {
            viewModel.process(Reset)
            navController.navigate(AppDestination.SurveyScreen.route)
        }

        is ErrorState -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = (screenState as ErrorState).message)
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        viewModel.process(Reset)
                    },
                ) {
                    Text("Go back")
                }
            }
        }

        InitialState -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Welcome")
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        viewModel.process(Load)
                    },
                ) {
                    Text("Start survey")
                }
            }
        }

        ProgressState -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator()
                    Spacer(Modifier.padding(16.dp))
                    Text("Loading...", color = Color.Green, fontSize = 24.sp)
                }
            }
        }
    }
}