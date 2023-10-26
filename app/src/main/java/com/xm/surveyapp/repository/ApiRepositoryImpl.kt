package com.xm.surveyapp.repository

import com.xm.surveyapp.model.Answer
import com.xm.surveyapp.model.Query
import com.xm.surveyapp.network.ApiClient
import com.xm.surveyapp.network.ApiRoutes
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiRepositoryImpl: ApiRepository {

    override suspend fun requestQuestions(): List<Query> =
        ApiClient.client.get(ApiRoutes.GET_QUESTIONS).body<List<Query>>()

    override suspend fun post(answer: Answer): Boolean {
        val response = ApiClient.client.post(ApiRoutes.POST_SUBMIT) {
            setBody(answer)
        }
        val statusCode = response.status.value
        return statusCode == 200
    }

}