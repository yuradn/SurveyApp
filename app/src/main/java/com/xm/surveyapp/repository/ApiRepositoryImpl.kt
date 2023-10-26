package com.xm.surveyapp.repository

import com.xm.surveyapp.model.AnswerDto
import com.xm.surveyapp.model.QueryDto
import com.xm.surveyapp.network.ApiClient
import com.xm.surveyapp.network.ApiRoutes
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiRepositoryImpl: ApiRepository {

    override suspend fun requestQuestions(): List<QueryDto> =
        ApiClient.client.get(ApiRoutes.GET_QUESTIONS).body<List<QueryDto>>()

    override suspend fun post(answerDto: AnswerDto): Boolean {
        val response = ApiClient.client.post(ApiRoutes.POST_SUBMIT) {
            setBody(answerDto)
        }
        val statusCode = response.status.value
        return statusCode == 200
    }

}