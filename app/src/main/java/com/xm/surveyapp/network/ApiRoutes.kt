package com.xm.surveyapp.network

object ApiRoutes {
    private const val BASE_URL:String = "https://xm-assignment.web.app"
    const val GET_QUESTIONS = "$BASE_URL/questions"
    const val POST_SUBMIT = "$BASE_URL/question/submit"
}