package com.besha.shinobihub.features.splash.presentation.viewmodel

import com.besha.shinobihub.appcore.mvi.ViewState

data class SplashViewState(
    val sessionId: String? = null,
) : ViewState
