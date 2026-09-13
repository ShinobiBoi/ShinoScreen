package com.besha.shinobihub.features.find.presenation.viewmodel

import app.cash.turbine.test
import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.domain.usecase.GetGenreListUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingAllUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingMoviesUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingPeopleUseCase
import com.besha.shinobihub.appcore.domain.usecase.GetTrendingTvUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchMovieUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchMultiUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchPeopleUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchTvUseCase
import com.besha.shinobihub.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FindViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTrendingAllUseCase: GetTrendingAllUseCase = mockk()
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase = mockk()
    private val getTrendingTvUseCase: GetTrendingTvUseCase = mockk()
    private val getTrendingPeopleUseCase: GetTrendingPeopleUseCase = mockk()
    private val searchMultiUseCase: SearchMultiUseCase = mockk()
    private val searchMovieUseCase: SearchMovieUseCase = mockk()
    private val searchTvUseCase: SearchTvUseCase = mockk()
    private val searchPeopleUseCase: SearchPeopleUseCase = mockk()
    private val getGenreListUseCase: GetGenreListUseCase = mockk()
    private val apiServices: ApiServices = mockk()

    private lateinit var viewModel: FindViewModel

    @Before
    fun setUp() {
        viewModel = FindViewModel(
            getTrendingAllUseCase,
            getTrendingMoviesUseCase,
            getTrendingTvUseCase,
            getTrendingPeopleUseCase,
            searchMultiUseCase,
            searchMovieUseCase,
            searchTvUseCase,
            searchPeopleUseCase,
            getGenreListUseCase,
            apiServices
        )
    }

    // region Genre Tests
    @Test
    fun `GetGenreList emits loading then success`() = runTest {
        val genres = listOf(Genre(id = 1, name = "Action"))
        coEvery { getGenreListUseCase() } returns DataState.Success(genres)

        viewModel.handleAction(FindAction.GetGenreList).test {
            assertThat((awaitItem() as FindResult.GenreList).state.isLoading).isTrue()
            val result = awaitItem() as FindResult.GenreList
            assertThat(result.state.data).isEqualTo(genres)
            awaitComplete()
        }
    }

    @Test
    fun `GetGenreList emits loading then error`() = runTest {
        val error = Throwable("Network Error")
        coEvery { getGenreListUseCase() } returns DataState.Error(error)

        viewModel.handleAction(FindAction.GetGenreList).test {
            assertThat((awaitItem() as FindResult.GenreList).state.isLoading).isTrue()
            val result = awaitItem() as FindResult.GenreList
            assertThat(result.state.errorThrowable).isEqualTo(error)
            awaitComplete()
        }
    }


    // endregion

    // region Media Loading Tests (Trending & Search)
    @Test
    fun `GetTrendingAll emits loading then success when filter is empty`() = runTest {
        val items = listOf(MediaItem(id = 1, media_type = MediaType.Movies))
        coEvery { getTrendingAllUseCase(1) } returns DataState.Success(items)

        viewModel.handleAction(FindAction.GetTrendingAll(emptyList())).test {
            assertThat((awaitItem() as FindResult.MediaLoaded).state.isLoading).isTrue()
            
            val result = awaitItem() as FindResult.MediaLoaded
            assertThat(result.state.data).isEqualTo(items)
            assertThat(result.state.isSuccess).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `GetTrendingMovies emits loading then error when use case fails`() = runTest {
        val error = Throwable("Server Error")
        coEvery { getTrendingMoviesUseCase(1) } returns DataState.Error(error)

        viewModel.handleAction(FindAction.GetTrendingMovies(emptyList())).test {
            assertThat((awaitItem() as FindResult.MediaLoaded).state.isLoading).isTrue()
            val result = awaitItem() as FindResult.MediaLoaded
            assertThat(result.state.errorThrowable).isEqualTo(error)
            awaitComplete()
        }
    }

    @Test
    fun `SearchMovie with query emits loading then success`() = runTest {
        val query = "Inception"
        val items = listOf(MediaItem(id = 1, media_type = MediaType.Movies))
        coEvery { searchMovieUseCase(query, 1) } returns DataState.Success(items)

        viewModel.handleAction(FindAction.SearchMovie(query, emptyList())).test {
            assertThat((awaitItem() as FindResult.MediaLoaded).state.isLoading).isTrue()
            val result = awaitItem() as FindResult.MediaLoaded
            assertThat(result.state.data).isEqualTo(items)
            awaitComplete()
        }
    }



    @Test
    fun `GetTrendingPeople emits loading then success`() = runTest {
        val items = listOf(MediaItem(id = 1, media_type = MediaType.People))
        coEvery { getTrendingPeopleUseCase(1) } returns DataState.Success(items)

        viewModel.handleAction(FindAction.GetTrendingPeople(emptyList())).test {
            assertThat((awaitItem() as FindResult.MediaLoaded).state.isLoading).isTrue()
            val result = awaitItem() as FindResult.MediaLoaded
            assertThat(result.state.data).isEqualTo(items)
            awaitComplete()
        }
    }

    @Test
    fun `SearchMulti emits loading then empty result`() = runTest {
        val query = "RandomQuery"
        coEvery { searchMultiUseCase(query, 1) } returns DataState.Empty

        viewModel.handleAction(FindAction.SearchMulti(query, emptyList())).test {
            assertThat((awaitItem() as FindResult.MediaLoaded).state.isLoading).isTrue()
            val result = awaitItem() as FindResult.MediaLoaded
            assertThat(result.state.isEmpty).isTrue()
            awaitComplete()
        }
    }
    // endregion

    @Test
    fun `ChangeMediaType emits Type result`() = runTest {
        val type = MediaType.Tv
        viewModel.handleAction(FindAction.ChangeMediaType(type)).test {
            val result = awaitItem() as FindResult.Type
            assertThat(result.state).isEqualTo(type)
            awaitComplete()
        }
    }

    @Test
    fun `ChangeQuery emits QueryChanged result`() = runTest {
        val query = "Hello"
        viewModel.handleAction(FindAction.ChangeQuery(query)).test {
            val result = awaitItem() as FindResult.QueryChanged
            assertThat(result.state.data).isEqualTo(query)
            awaitComplete()
        }
    }
}
