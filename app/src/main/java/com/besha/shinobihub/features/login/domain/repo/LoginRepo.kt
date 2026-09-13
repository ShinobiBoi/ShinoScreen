package com.besha.shinobihub.features.login.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.data.model.login.LoginResponse
import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.data.model.token.TokenResponse

interface LoginRepo {
    suspend fun login(loginRequest: LoginRequest): DataState<LoginResponse>

    suspend fun createToken(): DataState<TokenResponse>

    suspend fun createSession(sessionRequest: SessionRequest): DataState<String>
}
