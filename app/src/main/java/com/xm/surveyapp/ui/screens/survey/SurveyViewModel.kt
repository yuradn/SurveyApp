package com.xm.surveyapp.ui.screens.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xm.surveyapp.model.AnswerDto
import com.xm.surveyapp.repository.AnswersRepository
import com.xm.surveyapp.repository.ApiRepository
import com.xm.surveyapp.repository.QueriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SurveyViewModel(
    private val queriesRepository: QueriesRepository,
    private val answersRepository: AnswersRepository,
    private val apiRepository: ApiRepository
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
                        hasPrev = hasPrevPage(page),
                        isDelivered = queriesRepository.isDelivered(page),
                        isSubmitted = queriesRepository.isSubmitted(page),
                        submittedSize = queriesRepository.submittedSize(),
                        isPlaceSuccess = false
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
                        hasNext = hasNextPage(page, it.size),
                        isDelivered = queriesRepository.isDelivered(page),
                        isSubmitted = queriesRepository.isSubmitted(page),
                        submittedSize = queriesRepository.submittedSize(),
                        isPlaceSuccess = false
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
                        hasNext = hasNextPage(page, it.size),
                        isDelivered = queriesRepository.isDelivered(page),
                        isSubmitted = queriesRepository.isSubmitted(page),
                        submittedSize = queriesRepository.submittedSize(),
                        isPlaceSuccess = false
                    )
                }
            }

            SurveyAction.Reset -> {
                queriesRepository.clear()
                answersRepository.clear()
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

            SurveyAction.Post -> {
                post()
            }

            SurveyAction.Submit -> {
                submit()
            }
        }
    }

    private fun submit() {
        val state = uiState.value
        queriesRepository.setSubmit(state.page)
        _uiState.update {
            it.copy(
                isSubmitted = true,
                submittedSize = queriesRepository.submittedSize()
            )
        }
    }

    private fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = uiState.value
            val id = queriesRepository.load()[state.page].id
            val answer = state.answer
            val answerDto = AnswerDto(id = id, answer = answer)
            _uiState.update {
                it.copy(
                    isProgress = true
                )
            }
            val startTime = System.currentTimeMillis()
            try {
                val isSuccess =
                    withContext(Dispatchers.IO) { apiRepository.post(answerDto) }
                val diffTime = System.currentTimeMillis() - startTime
                if (diffTime < MIN_REQUEST_DELAY) {
                    delay(MIN_REQUEST_DELAY - diffTime)
                }
                if (isSuccess) {
                    _uiState.update {
                        queriesRepository.setDelivered(it.page, true)
                        it.copy(
                            isProgress = false,
                            isDelivered = true,
                            isPlaceSuccess = true
                        )
                    }
                } else {
                    _uiState.update {
                        queriesRepository.setDelivered(it.page, false)
                        it.copy(
                            isProgress = false,
                            isDelivered = false,
                            isPlaceSuccess = false
                        )
                    }
                }
            } catch (e: Exception) {
                val diffTime = System.currentTimeMillis() - startTime
                if (diffTime < MIN_REQUEST_DELAY) {
                    delay(MIN_REQUEST_DELAY - diffTime)
                }
                _uiState.update {
                    queriesRepository.setDelivered(it.page, false)
                    it.copy(
                        isProgress = false,
                        isDelivered = false,
                        isPlaceSuccess = false
                    )
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

    companion object {
        private const val MIN_REQUEST_DELAY = 1500L
    }
}