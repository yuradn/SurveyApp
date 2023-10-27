package com.xm.surveyapp.model


import com.xm.surveyapp.common.Question
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryDto(
    @SerialName("id")
    val id: Int,
    @SerialName("question")
    val question: Question
)