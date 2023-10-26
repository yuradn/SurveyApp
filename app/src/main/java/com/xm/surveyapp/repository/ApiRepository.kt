package com.xm.surveyapp.repository

import com.xm.surveyapp.model.AnswerDto
import com.xm.surveyapp.model.QueryDto

interface ApiRepository {
    suspend fun requestQuestions(): List<QueryDto>
    suspend fun post(answerDto: AnswerDto): Boolean
}