package com.tavoeh.secondfeature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LandingViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UIState<List<String>>> = MutableStateFlow(UIState.Loading)
    internal val uiState: StateFlow<UIState<List<String>>> = _uiState

    init {
        viewModelScope.launch {
            delay(3000) // Network Request
            _uiState.value = UIState.Data(listOf("Hello", "World"))
        }
    }

    internal sealed class UIState<out T> {
        object Loading : UIState<Nothing>()
        data class Data<T>(val data: T) : UIState<T>()
        data class Error(val error: Throwable) : UIState<Nothing>()
    }
}