package com.besha.shinobihub.features.find.presenation.viewmodel

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
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.features.find.domain.usecase.SearchMovieUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchMultiUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchPeopleUseCase
import com.besha.shinobihub.features.find.domain.usecase.SearchTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class FindViewModel
    @Inject
    constructor(
        val getTrendingAllUseCase: GetTrendingAllUseCase,
        val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
        val getTrendingTvUseCase: GetTrendingTvUseCase,
        val getTrendingPeopleUseCase: GetTrendingPeopleUseCase,
        val searchMultiUseCase: SearchMultiUseCase,
        val searchMovieUseCase: SearchMovieUseCase,
        val searchTvUseCase: SearchTvUseCase,
        val searchPeopleUseCase: SearchPeopleUseCase,
        val getGenreListUseCase: GetGenreListUseCase,
        val apiServices: ApiServices,
    ) : MVIBaseViewModel<FindAction, FindResult, FindViewState>() {
        override val defaultViewState: FindViewState
            get() = FindViewState()

        override fun handleAction(action: FindAction): Flow<FindResult> =
            flow {
                when (action) {
                    is FindAction.GetGenreList -> {
                        handleGetGenreList(this)
                    }

                    is FindAction.ToggleGenre -> {
                        handleToggleGenre(this, action.genre)
                    }

                    is FindAction.ChangeMediaType -> {
                        emit(FindResult.Type(action.type))
                    }

                    is FindAction.ChangeQuery -> {
                        emit(FindResult.QueryChanged(CommonViewState(data = action.query)))
                    }

                    // -------- Trending --------
                    is FindAction.GetTrendingAll -> {
                        handleNewMedia(this, action.filterList) {
                            getTrendingAllUseCase(it)
                        }
                    }

                    is FindAction.GetTrendingMovies -> {
                        handleNewMedia(this, action.filterList) {
                            getTrendingMoviesUseCase(it)
                        }
                    }

                    is FindAction.GetTrendingTv -> {
                        handleNewMedia(this, action.filterList) {
                            getTrendingTvUseCase(it)
                        }
                    }

                    is FindAction.GetTrendingPeople -> {
                        handleNewMedia(this, action.filterList) {
                            getTrendingPeopleUseCase(it)
                        }
                    }

                    // -------- Search --------
                    is FindAction.SearchMulti -> {
                        handleNewMedia(this, action.filterList) {
                            searchMultiUseCase(action.query, it)
                        }
                    }

                    is FindAction.SearchMovie -> {
                        handleNewMedia(this, action.filterList) {
                            searchMovieUseCase(action.query, it)
                        }
                    }

                    is FindAction.SearchTv -> {
                        handleNewMedia(this, action.filterList) {
                            searchTvUseCase(action.query, it)
                        }
                    }

                    is FindAction.SearchPeople -> {
                        handleNewMedia(this, action.filterList) {
                            searchPeopleUseCase(action.query, it)
                        }
                    }
                    is FindAction.ClearFilters -> {
                        handleClearFilter(this)
                    }
                }
            }

        private suspend fun handleClearFilter(flowCollector: FlowCollector<FindResult>) {
            val updatedGenres =
                viewStates.value.genres.data?.map {
                    it.copy(selected = false)
                }

            flowCollector.emit(FindResult.GenreList(CommonViewState(data = updatedGenres)))
            flowCollector.emit(FindResult.Type(MediaType.All))
        }

        private suspend fun handleToggleGenre(
            flowCollector: FlowCollector<FindResult>,
            genre: Genre,
        ) {
            val updatedGenres =
                viewStates.value.genres.data?.map {
                    if (it.id == genre.id) it.copy(selected = !it.selected) else it
                }

            flowCollector.emit(FindResult.GenreList(CommonViewState(data = updatedGenres)))
        }

        private suspend fun handleGetGenreList(flowCollector: FlowCollector<FindResult>) {
            flowCollector.emit(FindResult.GenreList(CommonViewState(isLoading = true)))

            when (val dataState = getGenreListUseCase()) {
                is DataState.Success -> {
                    flowCollector.emit(FindResult.GenreList(CommonViewState(data = dataState.data)))
                }
                is DataState.Error -> {
                    flowCollector.emit(FindResult.GenreList(CommonViewState(errorThrowable = dataState.throwable)))
                }
                is DataState.Empty -> {
                    flowCollector.emit(FindResult.GenreList(CommonViewState(isEmpty = true)))
                }
                else -> {}
            }
        }
    }

private suspend fun handleNewMedia(
    flowCollector: FlowCollector<FindResult>,
    filterList: List<Genre>,
    getPage: suspend (Int) -> DataState<List<MediaItem>>,
) {
    flowCollector.emit(FindResult.MediaLoaded(MediaViewState(isLoading = true)))

    var currentPage = 1

    if (filterList.isEmpty()) {
        // No filtering needed, just load first page
        when (val dataState = getPage(currentPage)) {
            is DataState.Success ->
                flowCollector.emit(
                    FindResult.MediaLoaded(MediaViewState(data = dataState.data, isSuccess = true)),
                )
            is DataState.Error ->
                flowCollector.emit(
                    FindResult.MediaLoaded(MediaViewState(errorThrowable = dataState.throwable)),
                )
            is DataState.Empty ->
                flowCollector.emit(
                    FindResult.MediaLoaded(MediaViewState(isEmpty = true)),
                )
            else -> {}
        }
    } else {
        val collectedItems = mutableListOf<MediaItem>()
        val selectedIds = filterList.mapNotNull { it.id }
        var keepFetching = true

        while (keepFetching) {
            when (val dataState = getPage(currentPage)) {
                is DataState.Success -> {
                    val filtered =
                        dataState.data.filter { item ->
                            item.genre_ids?.any { it in selectedIds } == true
                        }

                    collectedItems += filtered

                    // Stop if we reached enough items or no more data returned
                    if (collectedItems.size >= 20 || dataState.data.isEmpty()) {
                        keepFetching = false
                    } else {
                        currentPage++
                    }
                }

                is DataState.Empty -> {
                    keepFetching = false
                    if (collectedItems.isEmpty()) {
                        flowCollector.emit(
                            FindResult.MediaLoaded(MediaViewState(isEmpty = true)),
                        )
                        return
                    }
                }

                is DataState.Error -> {
                    flowCollector.emit(
                        FindResult.MediaLoaded(MediaViewState(errorThrowable = dataState.throwable)),
                    )
                    return
                }

                else -> keepFetching = false
            }
        }

        // Finally emit filtered results
        flowCollector.emit(
            FindResult.MediaLoaded(
                MediaViewState(
                    data = collectedItems.take(20),
                    isSuccess = collectedItems.isNotEmpty(),
                ),
            ),
        )
    }
}
