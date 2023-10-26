package com.xm.surveyapp.repository

import com.xm.surveyapp.model.Query

class QueriesRepositoryImpl() : QueriesRepository  {

    private val list: MutableList<Query> = mutableListOf()
    override fun getQuery(): List<Query> {
        return list
    }

    override fun setQuery(queries: List<Query>) {
        list.clear()
        list.addAll(queries)
    }
}