package com.xm.surveyapp.ui.screens.survey

import com.xm.surveyapp.ui.foundation.Action

sealed interface SurveyAction : Action {

    object Init : SurveyAction
    object NextPage : SurveyAction
    object PreviousPage : SurveyAction
    object Reset : SurveyAction
    class Edit(val text: String) : SurveyAction
    object Submit : SurveyAction
    object Post : SurveyAction
}