package com.xm.surveyapp.ui.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xm.surveyapp.repository.ApiRepository
import com.xm.surveyapp.repository.QueriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeViewModel(
    private val apiRepository: ApiRepository,
    private val queriesRepository: QueriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WelcomeScreenState>(InitialState)
    val uiState: StateFlow<WelcomeScreenState> = _uiState.asStateFlow()

    fun process(action: WelcomeAction) {
        when(action) {
            Load -> loading()
            Reset -> reset()
        }
    }

    private fun loading() {
        _uiState.update {
            ProgressState
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = apiRepository.requestQuestions()
                queriesRepository.save(list)
                withContext(Dispatchers.Main) {
                    _uiState.update {
                        CompleteState(list)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.update {
                        ErrorState(e.message ?: "Error!")
                    }
                }
            }
        }
    }

    private fun reset() {
        _uiState.update {
            InitialState
        }
    }
}