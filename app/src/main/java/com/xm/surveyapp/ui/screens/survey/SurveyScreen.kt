package com.xm.surveyapp.ui.screens.survey

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.xm.surveyapp.model.AnswerDto
import com.xm.surveyapp.model.QueryDto
import com.xm.surveyapp.services.LocalApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SurveyScreen(navController: NavHostController, viewModel: SurveyViewModel = koinViewModel()) {
    val screenState by viewModel.uiState.collectAsState()

    var page by rememberSaveable { mutableStateOf<Int>(0) }
    var size by rememberSaveable { mutableStateOf<Int>(0) }
    var questionText by rememberSaveable { mutableStateOf("") }
    var answerText by rememberSaveable { mutableStateOf("") }
    var hasNext by rememberSaveable { mutableStateOf(false) }
    var hasPrev by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.process(SurveyAction.Init)
    }

    DisposableEffect(key1 = screenState) {
        page = screenState.page
        size = screenState.size
        hasNext = screenState.hasNext
        hasPrev = screenState.hasPrev
        questionText = screenState.question
        answerText = screenState.answer
        onDispose { }
    }
    /*val list = LocalQueriesRepository.current.load()

    val queries = remember { list }
    var questionsSubmitted by remember { mutableIntStateOf(0) }

    var requestEvent by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var textFieldIsEnable by remember { mutableStateOf(true) }

    DisposableEffect(key1 = page, key2 = requestEvent) {
        questionsSubmitted = queries.filter { it.sent }.size
        page.let {
            textFieldIsEnable = queries[it].sent.not()
        }
        onDispose { }
    }*/

    Scaffold(
        topBar = {
            ActionBar(
                navController = navController,
                page = page.inc(),
                size = size,
                hasNext = hasNext,
                hasPrev = hasPrev,
                actionPrev = {
                    viewModel.process(SurveyAction.PreviousPage)
                }) {
                viewModel.process(SurveyAction.NextPage)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Questions submitted.",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = screenState.question, fontSize = 24.sp, color = Color.Black)
                Spacer(modifier = Modifier.padding(16.dp))
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = TextFieldValue(
                        text = answerText,
                        selection = TextRange(answerText.length)
                    ),
                    enabled = screenState.isSubmitted.not(),
                    onValueChange = {
                        viewModel.process(SurveyAction.Edit(it.text))
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.TopStart
                        ) {
                            if (answerText.isEmpty()) {
                                Text(
                                    "Type here for an answer...",
                                    fontSize = 24.sp,
                                    color = Color.DarkGray
                                )
                            }
                            innerTextField()
                        }
                    }
                    // placeholder = { Text("Type here for an answer...", fontSize = 24.sp) },
                )
                Spacer(modifier = Modifier.padding(48.dp))
                /*SubmitButton(page, answerText, queries[page]) {
                    queries[page].failure = it
                    queries[page].sent = it
                    requestEvent = System.currentTimeMillis()
                }*/
            }
        }
    }
}

@Composable
private fun SubmitButton(
    id: Int,
    answerText: String,
    queryDto: QueryDto,
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
                val answerDto = AnswerDto(id = id, answer = answerText)
                scope.launch(Dispatchers.IO) {
                    focusManager.clearFocus()
                    loading = true
                    val result = api.post(answerDto)
                    function.invoke(result)
                    loading = false
                    Log.d("Waagh!", "SubmitButton: $result")
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            enabled = queryDto.sent.not() && answerText.isNotEmpty()
        ) {
            if (queryDto.sent) {
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