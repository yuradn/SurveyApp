package com.xm.surveyapp.ui.screens.welcome

import com.xm.surveyapp.ui.foundation.Action

sealed interface WelcomeAction : Action

object Load : WelcomeAction
object Reset : WelcomeAction