package com.besha.shinobihub.features.watchlist.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.features.watchlist.domain.usecase.GetMovieWatchListUseCase
import com.besha.shinobihub.features.watchlist.domain.usecase.GetTvWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
    @Inject
    constructor(
        private val getMovieWatchListUseCase: GetMovieWatchListUseCase,
        private val getTvWatchListUseCase: GetTvWatchListUseCase,
        private val sessionManager: SessionManager,
    ) : MVIBaseViewModel<WatchListAction, WatchListResult, WatchListViewState>() {
        override val defaultViewState: WatchListViewState
            get() = WatchListViewState()

        override fun handleAction(action: WatchListAction): Flow<WatchListResult> =
            flow {
                when (action) {
                    is WatchListAction.GetMovieWatchList -> {
                        handleGetMediaWatchList(this) {
                            val sessionId = sessionManager.getSessionId().firstOrNull()
                            val accountId = sessionManager.getAccountId().firstOrNull()
                            if (sessionId != null && accountId != null) {
                                getMovieWatchListUseCase(accountId, sessionId)
                            } else {
                                DataState.Error(Throwable("Missing session or account ID"))
                            }
                        }
                    }
                    is WatchListAction.GetTvWatchList -> {
                        handleGetMediaWatchList(this) {
                            val sessionId = sessionManager.getSessionId().firstOrNull()
                            val accountId = sessionManager.getAccountId().firstOrNull()
                            if (sessionId != null && accountId != null) {
                                getTvWatchListUseCase(accountId, sessionId)
                            } else {
                                DataState.Error(Throwable("Missing session or account ID"))
                            }
                        }
                    }
                    is WatchListAction.ChangeMediaType -> {
                        emit(WatchListResult.ChangeMediaType(action.mediaType))
                    }
                }
            }

        private suspend fun handleGetMediaWatchList(
            flowCollector: FlowCollector<WatchListResult>,
            getWatchListMedia: suspend () -> DataState<List<MediaItem>>,
        ) {
            WatchListResult.MediaLoaded(MediaViewState(isLoading = true))

            when (val result = getWatchListMedia()) {
                is DataState.Success ->
                    flowCollector.emit(
                        WatchListResult.MediaLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        WatchListResult.MediaLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        WatchListResult.MediaLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }
    }
