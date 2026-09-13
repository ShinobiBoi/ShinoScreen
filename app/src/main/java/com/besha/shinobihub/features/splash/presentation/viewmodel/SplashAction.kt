package com.besha.shinobihub.features.splash.presentation.viewmodel

import com.besha.shinobihub.appcore.mvi.Action

sealed class SplashAction : Action {
    object GetSessionId : SplashAction()
}
