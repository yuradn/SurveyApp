package com.xm.surveyapp.repository

import com.xm.surveyapp.model.Query

interface QueriesRepository {
    fun load(): List<Query>
    fun save(queries: List<Query>)
}