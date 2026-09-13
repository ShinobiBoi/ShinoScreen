package com.besha.shinobihub.features.favourite.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.features.favourite.domain.usecase.GetMovieFavouriteUseCase
import com.besha.shinobihub.features.favourite.domain.usecase.GetTvFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel
    @Inject
    constructor(
        private val getMovieFavouriteUseCase: GetMovieFavouriteUseCase,
        private val getTvFavouriteUseCase: GetTvFavouriteUseCase,
        private val sessionManager: SessionManager,
    ) : MVIBaseViewModel<FavouriteAction, FavouriteResult, FavouriteViewState>() {
        override val defaultViewState: FavouriteViewState
            get() = FavouriteViewState()

        override fun handleAction(action: FavouriteAction): Flow<FavouriteResult> =
            flow {
                when (action) {
                    is FavouriteAction.GetMovieFavourite -> {
                        handleGetMediaFavourite(this) {
                            val sessionId = sessionManager.getSessionId().firstOrNull()
                            val accountId = sessionManager.getAccountId().firstOrNull()
                            if (sessionId != null && accountId != null) {
                                getMovieFavouriteUseCase(accountId, sessionId)
                            } else {
                                DataState.Error(Throwable("Missing session or account ID"))
                            }
                        }
                    }

                    is FavouriteAction.GetTvFavourite -> {
                        handleGetMediaFavourite(this) {
                            val sessionId = sessionManager.getSessionId().firstOrNull()
                            val accountId = sessionManager.getAccountId().firstOrNull()
                            if (sessionId != null && accountId != null) {
                                getTvFavouriteUseCase(accountId, sessionId)
                            } else {
                                DataState.Error(Throwable("Missing session or account ID"))
                            }
                        }
                    }

                    is FavouriteAction.ChangeMediaType -> {
                        emit(FavouriteResult.ChangeMediaType(action.mediaType))
                    }
                }
            }

        private suspend fun handleGetMediaFavourite(
            flowCollector: FlowCollector<FavouriteResult>,
            getFavouriteMedia: suspend () -> DataState<List<MediaItem>>,
        ) {
            // emit loading state
            flowCollector.emit(FavouriteResult.MediaLoaded(MediaViewState(isLoading = true)))

            when (val result = getFavouriteMedia()) {
                is DataState.Success ->
                    flowCollector.emit(
                        FavouriteResult.MediaLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )

                is DataState.Error ->
                    flowCollector.emit(
                        FavouriteResult.MediaLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )

                is DataState.Empty ->
                    flowCollector.emit(
                        FavouriteResult.MediaLoaded(MediaViewState(isEmpty = true)),
                    )

                else -> {}
            }
        }
    }
