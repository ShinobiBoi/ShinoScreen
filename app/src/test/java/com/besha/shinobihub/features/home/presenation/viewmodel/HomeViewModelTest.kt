package com.besha.shinobihub.features.home.presentaion.viewmodel

import app.cash.turbine.test
import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingAllUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingMoviesUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingPeopleUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingTvUseCase
import com.besha.shinobihub.features.home.data.model.account.AccountResponse
import com.besha.shinobihub.features.home.domain.usecase.GetAccountUseCase
import com.besha.shinobihub.features.home.domain.usecase.GetOnTheAirTvUseCase
import com.besha.shinobihub.features.home.domain.usecase.GetPopularMoviesUseCase
import com.besha.shinobihub.features.home.domain.usecase.GetPopularTvUseCase
import com.besha.shinobihub.features.home.domain.usecase.GetTopRatedMoviesUseCase
import com.besha.shinobihub.features.home.domain.usecase.GetTopRatedTvUseCase
import com.besha.shinobihub.features.home.domain.usecase.GetUpComingMoviesUseCase
import com.besha.shinobihub.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTrendingAllUseCase: GetTrendingAllUseCase = mockk()
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase = mockk()
    private val getTrendingTvUseCase: GetTrendingTvUseCase = mockk()
    private val getTrendingPeopleUseCase: GetTrendingPeopleUseCase = mockk()
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = mockk()
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase = mockk()
    private val getOnTheAirTvUseCase: GetOnTheAirTvUseCase = mockk()
    private val getPopularTvUseCase: GetPopularTvUseCase = mockk()
    private val getTopRatedTvUseCase: GetTopRatedTvUseCase = mockk()
    private val getAccountUseCase: GetAccountUseCase = mockk()
    private val sessionManager: SessionManager = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            getTrendingAllUseCase, getTrendingMoviesUseCase, getTrendingTvUseCase,
            getTrendingPeopleUseCase, getPopularMoviesUseCase, getTopRatedMoviesUseCase,
            getUpComingMoviesUseCase, getOnTheAirTvUseCase, getPopularTvUseCase,
            getTopRatedTvUseCase, getAccountUseCase, sessionManager
        )
    }

    @Test
    fun `GetTrendingAll emits loading then success`() = runTest {
        val movies = listOf(MediaItem(id = 1, media_type = MediaType.Movies))
        coEvery { getTrendingAllUseCase(1) } returns DataState.Success(movies)

        viewModel.handleAction(HomeAction.GetTrendingAll).test {
            val loading = awaitItem() as HomeResult.TrendingAllLoaded
            assertThat(loading.state.isLoading).isTrue()

            val success = awaitItem() as HomeResult.TrendingAllLoaded
            assertThat(success.state.data).isEqualTo(movies)
            assertThat(success.state.isSuccess).isTrue()

            awaitComplete()
        }
        coVerify(exactly = 1) { getTrendingAllUseCase(1) }
    }

    @Test
    fun `GetTrendingAll emits error state when use case fails`() = runTest {
        val error = RuntimeException("network down")
        coEvery { getTrendingAllUseCase(1) } returns DataState.Error(error)

        viewModel.handleAction(HomeAction.GetTrendingAll).test {
            awaitItem() // loading, not the focus of this test
            val result = awaitItem() as HomeResult.TrendingAllLoaded
            assertThat(result.state.errorThrowable).isEqualTo(error)
            awaitComplete()
        }
    }

    @Test
    fun `GetTrendingAll emits empty state when there is no data`() = runTest {
        coEvery { getTrendingAllUseCase(1) } returns DataState.Empty

        viewModel.handleAction(HomeAction.GetTrendingAll).test {
            awaitItem() // loading
            val result = awaitItem() as HomeResult.TrendingAllLoaded
            assertThat(result.state.isEmpty).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `GetAccount saves account id and emits success when session exists`() = runTest {
        val sessionId = "abc123"
        val account = AccountResponse(id = 42, username = "naruto")
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        coEvery { getAccountUseCase(sessionId) } returns DataState.Success(account)
        coEvery { sessionManager.saveAccountId(42) } just Runs

        viewModel.handleAction(HomeAction.GetAccount).test {
            val result = awaitItem() as HomeResult.AccountedLoaded
            assertThat(result.state.data).isEqualTo(account)
            awaitComplete()
        }
        coVerify { sessionManager.saveAccountId(42) }
    }

    @Test
    fun `GetAccount emits nothing when sessionId is null`() = runTest {
        every { sessionManager.getSessionId() } returns flowOf(null)

        viewModel.handleAction(HomeAction.GetAccount).test {
            awaitComplete() // no HomeResult should be emitted
        }
        coVerify(exactly = 0) { getAccountUseCase(any()) }
    }
}