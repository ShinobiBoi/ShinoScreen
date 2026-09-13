package com.besha.shinobihub.features.login.data.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.data.model.login.LoginResponse
import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.data.model.token.TokenResponse
import com.besha.shinobihub.features.login.domain.remote.LoginRemoteClient
import com.besha.shinobihub.features.login.domain.repo.LoginRepo
import javax.inject.Inject

class LoginRepoImp
    @Inject
    constructor(val remote: LoginRemoteClient) : LoginRepo {
        override suspend fun login(loginRequest: LoginRequest): DataState<LoginResponse> {
            return when (val result = remote.login(loginRequest)) {
                is DataState.Success -> {
                    DataState.Success(data = result.data)
                }
                is DataState.Empty -> {
                    DataState.Empty
                }
                is DataState.Error -> {
                    DataState.Error(throwable = result.throwable)
                }

                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun createToken(): DataState<TokenResponse> {
            return when (val result = remote.createToken()) {
                is DataState.Success -> {
                    DataState.Success(data = result.data)
                }
                is DataState.Empty -> {
                    DataState.Empty
                }
                is DataState.Error -> {
                    DataState.Error(throwable = result.throwable)
                }

                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }

        override suspend fun createSession(sessionRequest: SessionRequest): DataState<String> {
            return when (val result = remote.createSession(sessionRequest)) {
                is DataState.Success -> {
                    DataState.Success(data = result.data.session_id)
                }
                is DataState.Empty -> {
                    DataState.Empty
                }
                is DataState.Error -> {
                    DataState.Error(throwable = result.throwable)
                }

                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }
    }
