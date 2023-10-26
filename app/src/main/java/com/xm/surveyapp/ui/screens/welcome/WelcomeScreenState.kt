package com.xm.surveyapp.ui.screens.welcome

import com.xm.surveyapp.model.QueryDto

sealed interface WelcomeScreenState
object InitialState : WelcomeScreenState
object ProgressState : WelcomeScreenState
class CompleteState(val list: List<QueryDto>) : WelcomeScreenState
class ErrorState(val message: String) : WelcomeScreenState