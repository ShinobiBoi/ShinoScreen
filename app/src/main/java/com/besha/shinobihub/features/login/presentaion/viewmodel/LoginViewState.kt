package com.besha.shinobihub.features.login.presentaion.viewmodel

import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.ViewState
import com.besha.shinobihub.features.login.data.model.login.LoginResponse
import com.besha.shinobihub.features.login.data.model.token.TokenResponse

data class LoginViewState(
    val requestToken: CommonViewState<TokenResponse> = CommonViewState(),
    val sessionId: CommonViewState<String> = CommonViewState(),
    val loginResponse: CommonViewState<LoginResponse> = CommonViewState(),
) : ViewState
