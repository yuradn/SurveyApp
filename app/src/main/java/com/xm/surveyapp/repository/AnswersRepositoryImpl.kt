package com.xm.surveyapp.repository

import com.xm.surveyapp.common.Answer

class AnswersRepositoryImpl : AnswersRepository {

    private val answers: MutableMap<Int, Answer> = mutableMapOf()
    override fun saveAnswer(page: Int, answer: Answer) {
        answers[page] = answer
    }

    override fun loadAnswer(page: Int): Answer {
        return answers[page] ?: ""
    }

    override fun clear() {
        answers.clear()
    }
}