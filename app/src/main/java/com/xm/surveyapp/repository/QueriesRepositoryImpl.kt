package com.xm.surveyapp.repository

import com.xm.surveyapp.model.QueryDto

class QueriesRepositoryImpl : QueriesRepository  {

    private val list: MutableList<QueryDto> = mutableListOf()
    private val submittedMap: MutableMap<Int, Boolean> = mutableMapOf()
    private val deliveredMap: MutableMap<Int, Boolean> = mutableMapOf()
    override fun load(): List<QueryDto> {
        return list
    }

    override fun save(queries: List<QueryDto>) {
        list.clear()
        list.addAll(queries)
    }

    override fun setSubmit(index: Int) {
        submittedMap[index] = true
    }

    override fun setDelivered(index: Int, state: Boolean) {
        deliveredMap[index] = state
    }

    override fun isSubmitted(index: Int): Boolean {
        return submittedMap[index] ?: false
    }

    override fun isDelivered(index: Int): Boolean? {
        return deliveredMap[index]
    }

    override fun submittedSize(): Int {
        return submittedMap.size
    }

    override fun clear() {
        list.clear()
        submittedMap.clear()
        deliveredMap.clear()
    }

}