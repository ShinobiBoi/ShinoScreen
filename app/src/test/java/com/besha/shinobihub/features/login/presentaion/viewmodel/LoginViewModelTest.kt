package com.besha.shinobihub.features.login.presentaion.viewmodel

import app.cash.turbine.test
import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.data.model.login.LoginResponse
import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.data.model.token.TokenResponse
import com.besha.shinobihub.features.login.domain.usecase.CreateSessionUseCase
import com.besha.shinobihub.features.login.domain.usecase.CreateTokenUseCase
import com.besha.shinobihub.features.login.domain.usecase.LoginUseCase
import com.besha.shinobihub.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginUseCase: LoginUseCase = mockk()
    private val createSessionUseCase: CreateSessionUseCase = mockk()
    private val createTokenUseCase: CreateTokenUseCase = mockk()
    private val sessionManager: SessionManager = mockk()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel =
            LoginViewModel(
                loginUseCase,
                createSessionUseCase,
                createTokenUseCase,
                sessionManager,
            )
    }

    @Test
    fun `GetRequestToken emits success result`() =
        runTest {
            val tokenResponse = TokenResponse(success = true, request_token = "token123", expires_at = "2023-12-31")
            coEvery { createTokenUseCase() } returns DataState.Success(tokenResponse)

            viewModel.handleAction(LoginActions.GetRequestToken).test {
                val result = awaitItem() as LoginResult.RequestToken
                assertThat(result.state.data).isEqualTo(tokenResponse)
                awaitComplete()
            }
        }

    @Test
    fun `GetRequestToken emits error result`() =
        runTest {
            val error = Throwable("Failed to get token")
            coEvery { createTokenUseCase() } returns DataState.Error(error)

            viewModel.handleAction(LoginActions.GetRequestToken).test {
                val result = awaitItem() as LoginResult.RequestToken
                assertThat(result.state.errorThrowable).isEqualTo(error)
                awaitComplete()
            }
        }

    @Test
    fun `Login emits loading then success`() =
        runTest {
            val loginRequest = LoginRequest("user", "pass", "token")
            val loginResponse = LoginResponse(success = true, request_token = "token", expires_at = "2023-12-31")
            coEvery { loginUseCase(loginRequest) } returns DataState.Success(loginResponse)

            viewModel.handleAction(LoginActions.Login(loginRequest)).test {
                val loading = awaitItem() as LoginResult.Login
                assertThat(loading.state.isLoading).isTrue()

                val success = awaitItem() as LoginResult.Login
                assertThat(success.state.data).isEqualTo(loginResponse)
                awaitComplete()
            }
        }

    @Test
    fun `Login emits loading then error`() =
        runTest {
            val loginRequest = LoginRequest("user", "pass", "token")
            val error = Throwable("Unauthorized")
            coEvery { loginUseCase(loginRequest) } returns DataState.Error(error)

            viewModel.handleAction(LoginActions.Login(loginRequest)).test {
                awaitItem() // loading
                val result = awaitItem() as LoginResult.Login
                assertThat(result.state.errorThrowable).isEqualTo(error)
                awaitComplete()
            }
        }

    @Test
    fun `CreateSession emits success result`() =
        runTest {
            val sessionRequest = SessionRequest("token")
            val sessionResponse = "Sucess"
            coEvery { createSessionUseCase(sessionRequest) } returns DataState.Success(sessionResponse)

            viewModel.handleAction(LoginActions.CreateSession(sessionRequest)).test {
                val result = awaitItem() as LoginResult.SessionCreated
                assertThat(result.state.data).isEqualTo(sessionResponse)
                awaitComplete()
            }
        }

    @Test
    fun `SaveSessionId calls sessionManager`() =
        runTest {
            val sessionId = "sid123"
            coEvery { sessionManager.saveSessionId(sessionId) } just Runs

            viewModel.handleAction(LoginActions.SaveSessionId(sessionId)).test {
                awaitComplete()
            }
            coVerify { sessionManager.saveSessionId(sessionId) }
        }

    @Test
    fun `Logout calls sessionManager clearSession`() =
        runTest {
            coEvery { sessionManager.clearSession() } just Runs

            viewModel.handleAction(LoginActions.Logout).test {
                awaitComplete()
            }
            coVerify { sessionManager.clearSession() }
        }
}
