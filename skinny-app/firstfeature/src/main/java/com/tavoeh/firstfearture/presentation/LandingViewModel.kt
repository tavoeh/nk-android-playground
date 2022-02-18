package com.tavoeh.firstfearture.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import com.tavoeh.firstfearture.domain.model.FeatureType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LandingViewModel @Inject constructor(
    private val repository: FirstFeatureRepository,
    private val settings: FirstFeature.Settings
) : ViewModel() {

    val version: LiveData<String> = liveData { emit(settings.version) }

    private val _featuresStateFlow =
        MutableStateFlow<ViewState<List<String>>>(ViewState.Data(emptyList()))
    internal val featuresStateFlow: StateFlow<ViewState<List<String>>> = _featuresStateFlow

    // is this working on the viewModel scope?
    internal val featuresFlow = flow {
        emit(ViewState.Loading)
        runCatching { repository.getFeaturesByType(settings.type) }
            .onSuccess { emit(ViewState.Data(it)) }
            .onFailure { emit(ViewState.Failure(it)) }
    }

    init {
        getFeaturesByType(settings.type)
    }

    @VisibleForTesting
    fun getFeaturesByType(type: FeatureType) {
        viewModelScope.launch(Dispatchers.Main) {
            _featuresStateFlow.value = ViewState.Loading
            runCatching {
                val a = repository.getFeaturesByType(type)
                a
            }
                .onSuccess {
                    _featuresStateFlow.value = ViewState.Data(it)
                }
                .onFailure { _featuresStateFlow.value = ViewState.Failure(it) }
        }
    }

    fun multiply(a: Int, b: Int) = a * b
    fun getFeaturesByTypeSync(type: FeatureType) = repository.getFeaturesByTypeSync(type)

    internal sealed class ViewState<out T> {
        object Loading : ViewState<Nothing>()
        data class Data<T>(val data: T) : ViewState<T>()
        data class Failure(val error: Throwable) : ViewState<Nothing>()
    }
}
