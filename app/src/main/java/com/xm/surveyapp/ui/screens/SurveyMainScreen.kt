package com.xm.surveyapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xm.surveyapp.model.Answer
import com.xm.surveyapp.model.Query
import com.xm.surveyapp.ui.screens.services.LocalApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SurveyMainScreen() {
    var page by remember { mutableStateOf<Int?>(null) }
    val queries = remember { mutableListOf<Query>() }
    var questionsSubmitted by remember { mutableIntStateOf(0) }
    var answerText by rememberSaveable { mutableStateOf("") }
    var requestEvent by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var textFieldIsEnable by remember { mutableStateOf(true) }

    DisposableEffect(key1 = page, key2 = requestEvent) {
        questionsSubmitted = queries.filter { it.sent }.size
        page?.let {
            textFieldIsEnable = queries[it].sent.not()
        }
        onDispose { }
    }

    Scaffold(
        topBar = {
            if (page != null) {
                Appbar(page, queries.size,
                    actionPrev = {
                        if (page != null && page!! > 0) {
                            page = page!! - 1
                            answerText = queries[page!!].answer ?: ""
                        }
                    }, actionNext = {
                        if (page != null && page!! < queries.size - 1) {
                            page = page!! + 1
                            answerText = queries[page!!].answer ?: ""
                        }
                    })
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (page == null) {
                StartSurvey {
                    queries.addAll(it)
                    page = 0
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Questions submitted: $questionsSubmitted",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = queries[page!!].question, fontSize = 24.sp, color = Color.Black)
                    Spacer(modifier = Modifier.padding(16.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = answerText,
                        enabled = textFieldIsEnable,
                        onValueChange = {
                            queries[page!!].answer = it
                            answerText = it
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
                        placeholder = { Text("Type here for an answer...", fontSize = 24.sp) },
                    )
                    Spacer(modifier = Modifier.padding(48.dp))
                    SubmitButton(page!!, answerText, queries[page!!]) {
                        queries[page!!].failure = it
                        queries[page!!].sent = it
                        requestEvent = System.currentTimeMillis()
                    }
                }
            }
        }
    }
}

@Composable
private fun SubmitButton(
    id: Int,
    answerText: String,
    query: Query,
    function: (Boolean) -> Unit
) {
    val api = LocalApiRepository.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                Log.d("Waagh!", "SurveyMainScreen: Sent")
                val answer = Answer(id = id, answer = answerText)
                scope.launch(Dispatchers.IO) {
                    focusManager.clearFocus()
                    loading = true
                    val result = api.post(answer)
                    function.invoke(result)
                    loading = false
                    Log.d("Waagh!", "SubmitButton: $result")
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            enabled = query.sent.not() && answerText.isNotEmpty()
        ) {
            if (query.sent) {
                Text(
                    text = "Already submitted",
                    color = Color.DarkGray,
                    fontSize = 24.sp
                )
            } else {
                Text(text = "Submit", color = Color.Blue, fontSize = 24.sp)
            }
        }
        if (loading) {
            CircularProgressIndicator()
        }
    }

}