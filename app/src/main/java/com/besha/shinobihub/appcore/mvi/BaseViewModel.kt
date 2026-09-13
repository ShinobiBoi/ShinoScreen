package com.besha.shinobihub.appcore.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update

/**
 * Base class for view models that are not follow MVI pattern
 */
abstract class BaseViewModel<VS : ViewState> : ViewModel() {
    // default state
    abstract val defaultViewState: VS

    // state observer
    private val _viewStates: MutableStateFlow<VS> by lazy { MutableStateFlow(defaultViewState) }
    open val viewStates: StateFlow<VS> by lazy { _viewStates }
    open val viewEvents by lazy {
        _viewStates.shareIn(
            viewModelScope,
            SharingStarted.Lazily,
            replay = 0,
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    @Synchronized
    open fun emitState(stateReducer: (oldState: VS) -> VS) {
        val newState = stateReducer(_viewStates.value)
        if (_viewStates.value != newState) {
            _viewStates.update {
                newState
            }
        }
    }
}
