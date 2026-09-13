package com.besha.shinobihub.features.discover.presentaion.viewmodel

import android.util.Log
import app.cash.turbine.test
import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.domain.usecase.GetGenreListUseCase
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.features.discover.domain.usecase.GetDiscoverMovieUseCase
import com.besha.shinobihub.features.discover.domain.usecase.GetDiscoverTvUseCase
import com.besha.shinobihub.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DiscoverViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getGenreListUseCase: GetGenreListUseCase = mockk()
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase = mockk()
    private val getDiscoverTvUseCase: GetDiscoverTvUseCase = mockk()

    private lateinit var viewModel: DiscoverViewModel

    @Before
    fun setUp() {
        viewModel = DiscoverViewModel(
            getGenreListUseCase,
            getDiscoverMovieUseCase,
            getDiscoverTvUseCase
        )
    }

    // region GetGenreList Tests

    @Test
    fun `GetGenreList emits loading true, success result, then loading false`() = runTest {
        val genres = listOf(Genre(id = 1, name = "Action"))
        coEvery { getGenreListUseCase() } returns DataState.Success(genres)

        viewModel.handleAction(DiscoverAction.GetGenreList).test {
            val loadingStart = awaitItem() as DiscoverResult.Loading
            assertThat(loadingStart.state).isTrue()

            val success = awaitItem() as DiscoverResult.GenreList
            assertThat(success.state.data).isEqualTo(genres)
            assertThat(success.state.isSuccess).isTrue()

            val loadingEnd = awaitItem() as DiscoverResult.Loading
            assertThat(loadingEnd.state).isFalse()

            awaitComplete()
        }
    }

    @Test
    fun `GetGenreList emits loading true, error result, then loading false`() = runTest {
        val exception = Throwable("Network Error")
        coEvery { getGenreListUseCase() } returns DataState.Error(exception)

        viewModel.handleAction(DiscoverAction.GetGenreList).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val error = awaitItem() as DiscoverResult.GenreList
            assertThat(error.state.errorThrowable).isEqualTo(exception)

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    @Test
    fun `GetGenreList emits loading true, empty result, then loading false`() = runTest {
        coEvery { getGenreListUseCase() } returns DataState.Empty

        viewModel.handleAction(DiscoverAction.GetGenreList).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val empty = awaitItem() as DiscoverResult.GenreList
            assertThat(empty.state.isEmpty).isTrue()

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    // endregion

    // region GetDiscoverMovie Tests

    @Test
    fun `GetDiscoverMovie emits loading true, success result, then loading false`() = runTest {
        val genreId = "28"
        val movies = listOf(MediaItem(id = 1, media_type = MediaType.Movies))
        coEvery { getDiscoverMovieUseCase(genreId) } returns DataState.Success(movies)

        viewModel.handleAction(DiscoverAction.GetDiscoverMovie(genreId)).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val result = awaitItem() as DiscoverResult.MediaLoaded
            assertThat(result.state.data).isEqualTo(movies)
            assertThat(result.state.isSuccess).isTrue()

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    @Test
    fun `GetDiscoverMovie emits loading true, error result, then loading false`() = runTest {
        val genreId = "28"
        val exception = Throwable("API Error")
        coEvery { getDiscoverMovieUseCase(genreId) } returns DataState.Error(exception)

        viewModel.handleAction(DiscoverAction.GetDiscoverMovie(genreId)).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val result = awaitItem() as DiscoverResult.MediaLoaded
            assertThat(result.state.errorThrowable).isEqualTo(exception)

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    @Test
    fun `GetDiscoverMovie emits loading true, empty result, then loading false`() = runTest {
        val genreId = "28"
        coEvery { getDiscoverMovieUseCase(genreId) } returns DataState.Empty

        viewModel.handleAction(DiscoverAction.GetDiscoverMovie(genreId)).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val result = awaitItem() as DiscoverResult.MediaLoaded
            assertThat(result.state.isEmpty).isTrue()

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    // endregion

    // region GetDiscoverTv Tests

    @Test
    fun `GetDiscoverTv emits loading true, success result, then loading false`() = runTest {
        val genreId = "18"
        val tvShows = listOf(MediaItem(id = 1, media_type = MediaType.Tv))
        coEvery { getDiscoverTvUseCase(genreId) } returns DataState.Success(tvShows)

        viewModel.handleAction(DiscoverAction.GetDiscoverTv(genreId)).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val result = awaitItem() as DiscoverResult.MediaLoaded
            assertThat(result.state.data).isEqualTo(tvShows)
            assertThat(result.state.isSuccess).isTrue()

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    @Test
    fun `GetDiscoverTv emits loading true, error result, then loading false`() = runTest {
        val genreId = "18"
        val exception = Throwable("API Error")
        coEvery { getDiscoverTvUseCase(genreId) } returns DataState.Error(exception)

        viewModel.handleAction(DiscoverAction.GetDiscoverTv(genreId)).test {
            assertThat((awaitItem() as DiscoverResult.Loading).state).isTrue()

            val result = awaitItem() as DiscoverResult.MediaLoaded
            assertThat(result.state.errorThrowable).isEqualTo(exception)

            assertThat((awaitItem() as DiscoverResult.Loading).state).isFalse()
            awaitComplete()
        }
    }

    // endregion

    @Test
    fun `ChangeMediaType emits Type result`() = runTest {
        val type = MediaType.Tv
        viewModel.handleAction(DiscoverAction.ChangeMediaType(type)).test {
            val result = awaitItem() as DiscoverResult.Type
            assertThat(result.state).isEqualTo(type)
            awaitComplete()
        }
    }

}
