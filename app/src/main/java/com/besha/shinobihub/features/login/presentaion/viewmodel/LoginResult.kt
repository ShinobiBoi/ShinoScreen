package com.besha.shinobihub.features.login.presentaion.viewmodel

import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.Result
import com.besha.shinobihub.features.login.data.model.login.LoginResponse
import com.besha.shinobihub.features.login.data.model.token.TokenResponse

sealed class LoginResult : Result<LoginViewState> {
    data class RequestToken(val state: CommonViewState<TokenResponse>) : LoginResult() {
        override fun reduce(
            defaultState: LoginViewState,
            oldState: LoginViewState,
        ): LoginViewState {
            return oldState.copy(
                requestToken = state,
            )
        }
    }

    data class Login(val state: CommonViewState<LoginResponse>) : LoginResult() {
        override fun reduce(
            defaultState: LoginViewState,
            oldState: LoginViewState,
        ): LoginViewState {
            return oldState.copy(
                loginResponse = state,
            )
        }
    }

    data class SessionCreated(val state: CommonViewState<String>) : LoginResult() {
        override fun reduce(
            defaultState: LoginViewState,
            oldState: LoginViewState,
        ): LoginViewState {
            return oldState.copy(
                sessionId = state,
            )
        }
    }
}
