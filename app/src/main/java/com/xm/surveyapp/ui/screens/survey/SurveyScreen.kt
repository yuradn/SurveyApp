package com.xm.surveyapp.ui.screens.survey

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun SurveyScreen(navController: NavHostController, viewModel: SurveyViewModel = koinViewModel()) {
    val screenState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.process(SurveyAction.Init)
    }

    BackHandler {
        onBackPressed(viewModel, navController)
    }

    Scaffold(
        topBar = {
            ActionBar(
                page = screenState.page.inc(),
                size = screenState.size,
                hasNext = screenState.hasNext,
                hasPrev = screenState.hasPrev,
                actionPrev = {
                    viewModel.process(SurveyAction.PreviousPage)
                },
                actionNext = {
                viewModel.process(SurveyAction.NextPage)
                }) {
                onBackPressed(viewModel, navController)
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (screenState.isPlaceSuccess.not() && screenState.isDelivered != false) {
                    CounterSubmittedField(screenState)
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = screenState.question,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                }

                if (screenState.isSubmitted) {
                    DeliveryScreen(screenState)
                } else {
                    EnterAnswerScreen(screenState)
                }

                Spacer(modifier = Modifier.padding(48.dp))
                SubmitButton(
                    isEnabled = screenState.answer.isNotEmpty() &&
                            screenState.isSubmitted.not()
                            && screenState.isProgress.not(),
                    isSubmitted = screenState.isSubmitted,
                    action = {
                        viewModel.process(SurveyAction.Submit)
                        viewModel.process(SurveyAction.Post)
                    }
                )
            }
            if (screenState.isProgress) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun CounterSubmittedField(screenState: SurveyScreenState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Questions submitted: ${screenState.submittedSize}",
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
private fun EnterAnswerScreen(
    screenState: SurveyScreenState,
    viewModel: SurveyViewModel = koinViewModel()
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = TextFieldValue(
            text = screenState.answer,
            selection = TextRange(screenState.answer.length)
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
                if (screenState.answer.isEmpty()) {
                    Text(
                        "Type here for an answer...",
                        fontSize = 24.sp,
                        color = Color.DarkGray
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
private fun DeliveryScreen(
    screenState: SurveyScreenState
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        when {
            screenState.isPlaceSuccess -> {
                SuccessDeliveryScreen()
            }

            screenState.isDelivered == false -> {
                FailureDeliveryScreen()
            }
        }
        Text(
            text = screenState.answer,
            color = Color.DarkGray,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 24.dp, start = 8.dp)
        )
    }
}

@Composable
private fun FailureDeliveryScreen(viewModel: SurveyViewModel = koinViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Red)
            .padding(start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Failure",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier
                .weight(1f)
        )
        OutlinedButton(
            onClick = {
                viewModel.process(SurveyAction.Post)
            }, modifier = Modifier
                .weight(1f),
            border = BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(10)
        ) {
            Text(
                text = "Retry",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun SuccessDeliveryScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Green)
    ) {
        Text(
            text = "Success",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(32.dp)
        )
    }
}

private fun onBackPressed(
    viewModel: SurveyViewModel,
    navController: NavHostController
) {
    viewModel.process(SurveyAction.Reset)
    navController.popBackStack()
}

@Composable
private fun SubmitButton(
    isEnabled: Boolean,
    isSubmitted: Boolean,
    action: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                action.invoke()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            enabled = isEnabled,
            border = BorderStroke(0.dp, Color.LightGray),
            shape = RoundedCornerShape(10)
        ) {
            if (isSubmitted) {
                Text(
                    text = "Already submitted",
                    color = Color.DarkGray,
                    fontSize = 24.sp
                )
            } else {
                Text(text = "Submit", color = Color.Blue, fontSize = 24.sp)
            }
        }
    }

}