package com.xm.surveyapp.repository

import com.xm.surveyapp.model.Answer
import com.xm.surveyapp.model.Query

interface ApiRepository {
    suspend fun requestQuestions(): List<Query>
    suspend fun post(answer: Answer): Boolean
}