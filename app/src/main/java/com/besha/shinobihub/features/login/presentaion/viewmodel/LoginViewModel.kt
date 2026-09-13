package com.besha.shinobihub.features.login.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.domain.usecase.CreateSessionUseCase
import com.besha.shinobihub.features.login.domain.usecase.CreateTokenUseCase
import com.besha.shinobihub.features.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
        private val createSessionUseCase: CreateSessionUseCase,
        private val createTokenUseCase: CreateTokenUseCase,
        private val sessionManager: SessionManager,
    ) : MVIBaseViewModel<LoginActions, LoginResult, LoginViewState>() {
        override val defaultViewState: LoginViewState
            get() = LoginViewState()

        private suspend fun handleRequestToken(flowCollector: FlowCollector<LoginResult>) {
            when (val result = createTokenUseCase()) {
                is DataState.Success -> {
                    flowCollector.emit(LoginResult.RequestToken(state = CommonViewState(data = result.data)))
                }
                is DataState.Error -> {
                    flowCollector.emit(LoginResult.RequestToken(state = CommonViewState(errorThrowable = result.throwable)))
                }

                else -> {}
            }
        }

        override fun handleAction(action: LoginActions): Flow<LoginResult> =
            flow {
                when (action) {
                    is LoginActions.GetRequestToken -> {
                        handleRequestToken(this)
                    }

                    is LoginActions.Login -> {
                        handleLogin(this, action.loginRequest)
                    }

                    is LoginActions.CreateSession -> {
                        handleCreateSession(this, action.sessionRequest)
                    }

                    is LoginActions.SaveSessionId -> {
                        sessionManager.saveSessionId(action.sessionId)
                    }
                    is LoginActions.Logout -> {
                        sessionManager.clearSession()
                    }
                }
            }

        private suspend fun handleCreateSession(
            flowCollector: FlowCollector<LoginResult>,
            sessionRequest: SessionRequest,
        ) {
            when (val result = createSessionUseCase(sessionRequest)) {
                is DataState.Success -> {
                    flowCollector.emit(LoginResult.SessionCreated(state = CommonViewState(data = result.data)))
                }
                is DataState.Error -> {
                    flowCollector.emit(LoginResult.SessionCreated(state = CommonViewState(errorThrowable = result.throwable)))
                }
                else -> {}
            }
        }

        private suspend fun handleLogin(
            flowCollector: FlowCollector<LoginResult>,
            loginRequest: LoginRequest,
        ) {
            flowCollector.emit(LoginResult.Login(state = CommonViewState(isLoading = true)))

            val result = loginUseCase(loginRequest)

            when (result) {
                is DataState.Success -> {
                    flowCollector.emit(LoginResult.Login(state = CommonViewState(data = result.data)))
                }
                is DataState.Error -> {
                    flowCollector.emit(LoginResult.Login(state = CommonViewState(errorThrowable = result.throwable)))
                }
                else -> {
                    flowCollector.emit(LoginResult.Login(state = CommonViewState(errorThrowable = Throwable("unknown error"))))
                }
            }
        }
    }
