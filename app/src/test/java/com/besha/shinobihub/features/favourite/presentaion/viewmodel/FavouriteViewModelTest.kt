package com.besha.shinobihub.features.favourite.presentaion.viewmodel

import app.cash.turbine.test
import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.features.favourite.domain.usecase.GetMovieFavouriteUseCase
import com.besha.shinobihub.features.favourite.domain.usecase.GetTvFavouriteUseCase
import com.besha.shinobihub.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMovieFavouriteUseCase: GetMovieFavouriteUseCase = mockk()
    private val getTvFavouriteUseCase: GetTvFavouriteUseCase = mockk()
    private val sessionManager: SessionManager = mockk()

    private lateinit var viewModel: FavouriteViewModel

    @Before
    fun setUp() {
        viewModel = FavouriteViewModel(
            getMovieFavouriteUseCase,
            getTvFavouriteUseCase,
            sessionManager
        )
    }

    // region GetMovieFavourite Tests

    @Test
    fun `GetMovieFavourite emits loading then success when session and account IDs exist`() = runTest {
        val sessionId = "session_123"
        val accountId = 1
        val movies = listOf(MediaItem(id = 1, media_type = MediaType.Movies))
        
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        every { sessionManager.getAccountId() } returns flowOf(accountId)
        coEvery { getMovieFavouriteUseCase(accountId, sessionId) } returns DataState.Success(movies)

        viewModel.handleAction(FavouriteAction.GetMovieFavourite).test {
            val loading = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(loading.media.isLoading).isTrue()

            val success = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(success.media.data).isEqualTo(movies)
            assertThat(success.media.isSuccess).isTrue()

            awaitComplete()
        }
    }

    @Test
    fun `GetMovieFavourite emits loading then error when session ID is missing`() = runTest {
        every { sessionManager.getSessionId() } returns flowOf(null)
        every { sessionManager.getAccountId() } returns flowOf(1)

        viewModel.handleAction(FavouriteAction.GetMovieFavourite).test {
            val loading = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(loading.media.isLoading).isTrue()

            val error = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(error.media.errorThrowable?.message).isEqualTo("Missing session or account ID")

            awaitComplete()
        }
    }

    @Test
    fun `GetMovieFavourite emits loading then error when account ID is missing`() = runTest {
        every { sessionManager.getSessionId() } returns flowOf("session_123")
        every { sessionManager.getAccountId() } returns flowOf(null)

        viewModel.handleAction(FavouriteAction.GetMovieFavourite).test {
            val loading = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(loading.media.isLoading).isTrue()

            val error = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(error.media.errorThrowable?.message).isEqualTo("Missing session or account ID")

            awaitComplete()
        }
    }

    @Test
    fun `GetMovieFavourite emits loading then empty when use case returns empty`() = runTest {
        val sessionId = "session_123"
        val accountId = 1
        
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        every { sessionManager.getAccountId() } returns flowOf(accountId)
        coEvery { getMovieFavouriteUseCase(accountId, sessionId) } returns DataState.Empty

        viewModel.handleAction(FavouriteAction.GetMovieFavourite).test {
            awaitItem() // loading
            val empty = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(empty.media.isEmpty).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `GetMovieFavourite emits loading then error when use case returns error`() = runTest {
        val sessionId = "session_123"
        val accountId = 1
        val exception = Throwable("Network Error")
        
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        every { sessionManager.getAccountId() } returns flowOf(accountId)
        coEvery { getMovieFavouriteUseCase(accountId, sessionId) } returns DataState.Error(exception)

        viewModel.handleAction(FavouriteAction.GetMovieFavourite).test {
            awaitItem() // loading
            val error = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(error.media.errorThrowable).isEqualTo(exception)
            awaitComplete()
        }
    }

    // endregion

    // region GetTvFavourite Tests

    @Test
    fun `GetTvFavourite emits loading then success when session and account IDs exist`() = runTest {
        val sessionId = "session_123"
        val accountId = 1
        val tvSeries = listOf(MediaItem(id = 2, media_type = MediaType.Tv))
        
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        every { sessionManager.getAccountId() } returns flowOf(accountId)
        coEvery { getTvFavouriteUseCase(accountId, sessionId) } returns DataState.Success(tvSeries)

        viewModel.handleAction(FavouriteAction.GetTvFavourite).test {
            val loading = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(loading.media.isLoading).isTrue()

            val success = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(success.media.data).isEqualTo(tvSeries)
            assertThat(success.media.isSuccess).isTrue()

            awaitComplete()
        }
    }

    @Test
    fun `GetTvFavourite emits loading then error when session ID is missing`() = runTest {
        every { sessionManager.getSessionId() } returns flowOf(null)
        every { sessionManager.getAccountId() } returns flowOf(1)

        viewModel.handleAction(FavouriteAction.GetTvFavourite).test {
            awaitItem() // loading
            val error = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(error.media.errorThrowable?.message).isEqualTo("Missing session or account ID")
            awaitComplete()
        }
    }

    @Test
    fun `GetTvFavourite emits loading then error when account ID is missing`() = runTest {
        every { sessionManager.getSessionId() } returns flowOf("session_123")
        every { sessionManager.getAccountId() } returns flowOf(null)

        viewModel.handleAction(FavouriteAction.GetTvFavourite).test {
            awaitItem() // loading
            val error = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(error.media.errorThrowable?.message).isEqualTo("Missing session or account ID")
            awaitComplete()
        }
    }

    @Test
    fun `GetTvFavourite emits loading then empty when use case returns empty`() = runTest {
        val sessionId = "session_123"
        val accountId = 1
        
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        every { sessionManager.getAccountId() } returns flowOf(accountId)
        coEvery { getTvFavouriteUseCase(accountId, sessionId) } returns DataState.Empty

        viewModel.handleAction(FavouriteAction.GetTvFavourite).test {
            awaitItem() // loading
            val empty = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(empty.media.isEmpty).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `GetTvFavourite emits loading then error when use case returns error`() = runTest {
        val sessionId = "session_123"
        val accountId = 1
        val exception = Throwable("API Error")
        
        every { sessionManager.getSessionId() } returns flowOf(sessionId)
        every { sessionManager.getAccountId() } returns flowOf(accountId)
        coEvery { getTvFavouriteUseCase(accountId, sessionId) } returns DataState.Error(exception)

        viewModel.handleAction(FavouriteAction.GetTvFavourite).test {
            awaitItem() // loading
            val error = awaitItem() as FavouriteResult.MediaLoaded
            assertThat(error.media.errorThrowable).isEqualTo(exception)
            awaitComplete()
        }
    }

    // endregion

    @Test
    fun `ChangeMediaType emits ChangeMediaType result`() = runTest {
        val mediaType = MediaType.Tv
        
        viewModel.handleAction(FavouriteAction.ChangeMediaType(mediaType)).test {
            val result = awaitItem() as FavouriteResult.ChangeMediaType
            assertThat(result.mediaType).isEqualTo(mediaType)
            awaitComplete()
        }
    }
}
