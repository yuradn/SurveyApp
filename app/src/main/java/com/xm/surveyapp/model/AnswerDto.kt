package com.xm.surveyapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerDto(
    @SerialName("id")
    val id: Int,
    @SerialName("question")
    val answer: String
)