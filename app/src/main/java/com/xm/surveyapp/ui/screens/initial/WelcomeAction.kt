package com.xm.surveyapp.ui.screens.initial

import com.xm.surveyapp.ui.foundation.Action

sealed interface WelcomeAction : Action

object Load : WelcomeAction
object Reset : WelcomeAction