package com.xm.surveyapp.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Query(
    @SerialName("id")
    val id: Int,
    @SerialName("question")
    val question: String,
    @kotlinx.serialization.Transient
    var answer: String? = null,
    @kotlinx.serialization.Transient
    var sent: Boolean = false,
    @kotlinx.serialization.Transient
    var failure: Boolean? = null
)