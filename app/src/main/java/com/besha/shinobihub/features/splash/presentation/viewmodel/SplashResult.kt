package com.besha.shinobihub.features.splash.presentation.viewmodel

import com.besha.shinobihub.appcore.mvi.Result

sealed class SplashResult : Result<SplashViewState> {
    data class SessionIdLoad(val sessionId: String?) : SplashResult() {
        override fun reduce(
            defaultState: SplashViewState,
            oldState: SplashViewState,
        ): SplashViewState {
            return oldState.copy(
                sessionId = sessionId,
            )
        }
    }
}
