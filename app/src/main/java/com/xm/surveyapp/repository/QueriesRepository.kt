package com.xm.surveyapp.repository

import com.xm.surveyapp.model.QueryDto

interface QueriesRepository {
    fun load(): List<QueryDto>
    fun save(queries: List<QueryDto>)
}