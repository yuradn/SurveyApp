package com.xm.surveyapp.repository

import com.xm.surveyapp.model.QueryDto

class QueriesRepositoryImpl : QueriesRepository  {

    private val list: MutableList<QueryDto> = mutableListOf()
    override fun load(): List<QueryDto> {
        return list
    }

    override fun save(queries: List<QueryDto>) {
        list.clear()
        list.addAll(queries)
    }
}