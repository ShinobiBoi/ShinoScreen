package com.besha.shinobihub.features.discover.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.usecase.GetGenreListUseCase
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.features.discover.domain.usecase.GetDiscoverMovieUseCase
import com.besha.shinobihub.features.discover.domain.usecase.GetDiscoverTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
    @Inject
    constructor(
        val getGenreListUseCase: GetGenreListUseCase,
        val getDiscoverMovieUseCase: GetDiscoverMovieUseCase,
        val getDiscoverTvUseCase: GetDiscoverTvUseCase,
    ) : MVIBaseViewModel<DiscoverAction, DiscoverResult, DiscoverViewState>() {
        override val defaultViewState: DiscoverViewState
            get() = DiscoverViewState()

        override fun handleAction(action: DiscoverAction): Flow<DiscoverResult> =
            flow {
                when (action) {
                    is DiscoverAction.GetDiscoverMovie -> {
                        handleNewMedia(this, action.genreId) {
                            getDiscoverMovieUseCase(it)
                        }
                    }

                    is DiscoverAction.GetDiscoverTv -> {
                        handleNewMedia(this, action.genreId) {
                            getDiscoverTvUseCase(it)
                        }
                    }

                    is DiscoverAction.ChangeMediaType -> {
                        emit(DiscoverResult.Type(state = action.type))
                    }

                    is DiscoverAction.GetGenreList -> {
                        handleGetGenreList(this)
                    }

                    is DiscoverAction.ToggleGenre -> {
                        handleToggleGenre(this, action.genreId)
                    }
                    is DiscoverAction.ClearFilters -> {
                        clearFilters(this)
                    }
                }
            }

        private suspend fun handleNewMedia(
            flowCollector: FlowCollector<DiscoverResult>,
            genreId: String,
            getMedia: suspend (string: String) -> DataState<List<MediaItem>>,
        ) {
            flowCollector.emit(DiscoverResult.Loading(true))

            when (val result = getMedia(genreId)) {
                is DataState.Success ->
                    flowCollector.emit(
                        DiscoverResult.MediaLoaded(CommonViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        DiscoverResult.MediaLoaded(CommonViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        DiscoverResult.MediaLoaded(CommonViewState(isEmpty = true)),
                    )
                else -> {}
            }
            flowCollector.emit(DiscoverResult.Loading(false))
        }

        private suspend fun handleToggleGenre(
            flowCollector: FlowCollector<DiscoverResult>,
            genreId: Int,
        ) {
            val updatedGenres =
                viewStates.value.genres.data?.map {
                    if (it.id == genreId) it.copy(selected = !it.selected) else it
                }

            flowCollector.emit(DiscoverResult.GenreList(CommonViewState(data = updatedGenres)))
        }

        private suspend fun clearFilters(flowCollector: FlowCollector<DiscoverResult>) {
            val updatedGenres =
                viewStates.value.genres.data?.map {
                    it.copy(selected = false)
                }

            flowCollector.emit(DiscoverResult.GenreList(CommonViewState(data = updatedGenres)))
        }

        private suspend fun handleGetGenreList(flowCollector: FlowCollector<DiscoverResult>) {
            flowCollector.emit(DiscoverResult.Loading(true))

            when (val dataState = getGenreListUseCase()) {
                is DataState.Success -> {
                    flowCollector.emit(DiscoverResult.GenreList(CommonViewState(data = dataState.data, isSuccess = true)))
                }

                is DataState.Error -> {
                    flowCollector.emit(DiscoverResult.GenreList(CommonViewState(errorThrowable = dataState.throwable)))
                }

                is DataState.Empty -> {
                    flowCollector.emit(DiscoverResult.GenreList(CommonViewState(isEmpty = true)))
                }

                else -> {}
            }

            flowCollector.emit(DiscoverResult.Loading(false))
        }
    }
