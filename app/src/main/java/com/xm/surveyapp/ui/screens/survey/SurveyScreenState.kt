package com.xm.surveyapp.ui.screens.survey

data class SurveyScreenState(
    val isProgress: Boolean = false,
    val page: Int = 0,
    val size: Int = 0,
    val hasNext: Boolean = false,
    val hasPrev: Boolean = false,
    val question: String = "",
    val answer: String = "",
    val isSubmitted: Boolean = false,
    val isDelivered: Boolean = false
)