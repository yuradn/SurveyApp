package com.xm.surveyapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    @SerialName("id")
    val id: Int,
    @SerialName("question")
    val answer: String
)