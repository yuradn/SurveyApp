package com.xm.surveyapp.repository

import com.xm.surveyapp.model.Query

class QueriesRepositoryImpl : QueriesRepository  {

    private val list: MutableList<Query> = mutableListOf()
    override fun load(): List<Query> {
        return list
    }

    override fun save(queries: List<Query>) {
        list.clear()
        list.addAll(queries)
    }
}