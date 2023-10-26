package com.xm.surveyapp.ui.screens.survey

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ActionBar(
    navController: NavHostController,
    page: Int,
    size: Int,
    hasNext: Boolean,
    hasPrev: Boolean,
    actionPrev: () -> Unit,
    actionNext: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Question ${page}/$size")
        },
        navigationIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },

        actions = {
            TextButton(onClick = {
                keyboardController?.hide()
                actionPrev.invoke()
            }, enabled = hasPrev) {
                Text(text = "Previous", color = if (hasPrev) Color.Blue else Color.Gray)
            }
            TextButton(onClick = {
                keyboardController?.hide()
                actionNext.invoke()
            }, enabled = hasNext) {
                Text(text = "Next", color = if (hasNext) Color.Blue else Color.Gray)
            }
        }
    )
}