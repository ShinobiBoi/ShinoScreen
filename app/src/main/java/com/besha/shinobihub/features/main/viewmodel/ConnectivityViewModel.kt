package com.besha.shinobihub.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.besha.shinobihub.utils.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    observer: ConnectivityObserver
) : ViewModel() {
    val isConnected = observer.isConnected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}