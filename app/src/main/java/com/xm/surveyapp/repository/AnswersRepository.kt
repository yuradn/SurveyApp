package com.xm.surveyapp.repository

import com.xm.surveyapp.common.Answer

interface AnswersRepository {
    fun saveAnswer(page: Int, answer: Answer)
    fun loadAnswer(page: Int): Answer
    fun clear()
}