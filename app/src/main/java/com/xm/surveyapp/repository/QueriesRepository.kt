package com.xm.surveyapp.repository

import com.xm.surveyapp.model.QueryDto

interface QueriesRepository {
    fun load(): List<QueryDto>
    fun save(queries: List<QueryDto>)
    fun setSubmit(index: Int)
    fun setDelivered(index: Int, state: Boolean)
    fun isSubmitted(index: Int): Boolean
    fun isDelivered(index: Int): Boolean?
    fun submittedSize(): Int
    fun clear()
}