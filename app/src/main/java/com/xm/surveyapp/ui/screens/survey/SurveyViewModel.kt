package com.xm.surveyapp.ui.screens.survey

import androidx.lifecycle.ViewModel
import com.xm.surveyapp.repository.AnswersRepository
import com.xm.surveyapp.repository.QueriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SurveyViewModel(
    private val queriesRepository: QueriesRepository,
    private val answersRepository: AnswersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SurveyScreenState>(SurveyScreenState())
    val uiState: StateFlow<SurveyScreenState> = _uiState.asStateFlow()

    fun process(action: SurveyAction) {
        when (action) {
            SurveyAction.Init -> {
                val queries = queriesRepository.load()
                if (queries.isEmpty()) return
                _uiState.update {
                    val page = 0
                    it.copy(
                        page = page,
                        size = queries.size,
                        question = queries[it.page].question,
                        answer = answersRepository.loadAnswer(it.page),
                        hasNext = hasNextPage(page, queries.size),
                        hasPrev = hasPrevPage(page)
                    )
                }
            }

            SurveyAction.NextPage -> {
                _uiState.update {
                    val page = it.page.inc()
                    it.copy(
                        page = page,
                        question = queriesRepository.load()[page].question,
                        answer = answersRepository.loadAnswer(page),
                        hasPrev = hasPrevPage(page),
                        hasNext = hasNextPage(page, it.size)
                    )
                }
            }

            SurveyAction.PreviousPage -> {
                _uiState.update {
                    val page = it.page.dec()
                    it.copy(
                        page = page,
                        question = queriesRepository.load()[page].question,
                        answer = answersRepository.loadAnswer(page),
                        hasPrev = hasPrevPage(page),
                        hasNext = hasNextPage(page, it.size)
                    )
                }
            }

            SurveyAction.Reset -> {
                _uiState.update {
                    SurveyScreenState()
                }
            }

            is SurveyAction.Edit -> {
                _uiState.update {
                    val text = action.text
                    answersRepository.saveAnswer(it.page, text)
                    it.copy(answer = action.text)
                }
            }
        }
    }

    private fun hasNextPage(page: Int, size: Int): Boolean {
        return page.inc() < size
    }

    private fun hasPrevPage(page: Int): Boolean {
        return page > 0
    }
}