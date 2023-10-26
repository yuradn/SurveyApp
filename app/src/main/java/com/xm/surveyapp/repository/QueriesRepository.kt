package com.xm.surveyapp.repository

import com.xm.surveyapp.model.Query

interface QueriesRepository {
    fun getQuery(): List<Query>

    fun setQuery(queries: List<Query>)
}