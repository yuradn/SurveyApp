package com.xm.surveyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xm.surveyapp.model.Query
import com.xm.surveyapp.ui.screens.services.LocalApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StartSurvey(action: (List<Query>) -> Unit) {
    val scope = rememberCoroutineScope()
    val api = LocalApiRepository.current

    val loading = remember { mutableStateOf(false) }

    if (loading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Welcome")
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    loading.value = true
                    scope.launch(Dispatchers.IO) {
                        val queries = api.requestQuestions()
                        action.invoke(queries)
                    }
                },
            ) {
                Text("Start survey")
            }
        }
    }
}