package com.xm.surveyapp.ui.screens.initial

import com.xm.surveyapp.model.Query

sealed interface WelcomeScreenState
object InitialState : WelcomeScreenState
object ProgressState : WelcomeScreenState
class CompleteState(val list: List<Query>) : WelcomeScreenState
class ErrorState(val message: String) : WelcomeScreenState