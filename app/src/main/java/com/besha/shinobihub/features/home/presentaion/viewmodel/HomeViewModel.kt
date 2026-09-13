package com.besha.shinobihub.features.home.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.usecase.*
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.features.home.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getTrendingAllUseCase: GetTrendingAllUseCase,
        private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
        private val getTrendingTvUseCase: GetTrendingTvUseCase,
        private val getTrendingPeopleUseCase: GetTrendingPeopleUseCase,
        private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
        private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
        private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
        private val getOnTheAirTvUseCase: GetOnTheAirTvUseCase,
        private val getPopularTvUseCase: GetPopularTvUseCase,
        private val getTopRatedTvUseCase: GetTopRatedTvUseCase,
        private val getAccountUseCase: GetAccountUseCase,
        private val sessionManager: SessionManager,
    ) : MVIBaseViewModel<HomeAction, HomeResult, HomeViewState>() {
        override val defaultViewState: HomeViewState
            get() = HomeViewState()

        override fun handleAction(action: HomeAction): Flow<HomeResult> =
            flow {
                when (action) {
                    is HomeAction.ChangeMediaType -> {
                        emit(HomeResult.Type(CommonViewState(data = action.type)))
                    }

                    is HomeAction.GetTrendingAll -> {
                        handleGetTrendingAll(this)
                    }

                    is HomeAction.GetTrendingMovies -> {
                        handleGetTrendingMovies(this)
                    }

                    is HomeAction.GetTrendingTv -> {
                        handleGetTrendingTv(this)
                    }

                    is HomeAction.GetTrendingPeople -> {
                        handleGetTrendingPeople(this)
                    }

                    is HomeAction.GetPopularMovies -> {
                        handleGetPopularMovies(this)
                    }

                    is HomeAction.GetTopRatedMovies -> {
                        handleGetTopRatedMovies(this)
                    }

                    is HomeAction.GetUpComingMovies -> {
                        handleGetUpComingMovies(this)
                    }

                    is HomeAction.GetOnTheAirTv -> {
                        handleGetOnTheAirTv(this)
                    }

                    is HomeAction.GetPopularTv -> {
                        handleGetPopularTv(this)
                    }

                    is HomeAction.GetTopRatedTv -> {
                        handleGetTopRatedTv(this)
                    }

                    is HomeAction.GetAccount -> {
                        handleHetAccount(this, sessionManager.getSessionId().firstOrNull())
                    }
                }
            }

        private suspend fun handleHetAccount(
            flowCollector: FlowCollector<HomeResult>,
            sessionId: String?,
        ) {
            if (sessionId != null) {
                when (val result = getAccountUseCase(sessionId)) {
                    is DataState.Success -> {
                        flowCollector.emit(HomeResult.AccountedLoaded(CommonViewState(data = result.data, isSuccess = true)))
                        sessionManager.saveAccountId(result.data.id!!)
                    }
                    is DataState.Error ->
                        flowCollector.emit(
                            HomeResult.AccountedLoaded(CommonViewState(errorThrowable = result.throwable)),
                        )
                    is DataState.Empty ->
                        flowCollector.emit(
                            HomeResult.AccountedLoaded(CommonViewState(isEmpty = true)),
                        )
                    else -> {}
                }
            }
        }

        // ---------------------
        // Trending Handlers
        // ---------------------

        private suspend fun handleGetTrendingAll(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.TrendingAllLoaded(MediaViewState(isLoading = true)))

            when (val result = getTrendingAllUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.TrendingAllLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.TrendingAllLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.TrendingAllLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetTrendingMovies(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.TrendingMoviesLoaded(MediaViewState(isLoading = true)))

            when (val result = getTrendingMoviesUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.TrendingMoviesLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.TrendingMoviesLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.TrendingMoviesLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetTrendingTv(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.TrendingTvLoaded(MediaViewState(isLoading = true)))

            when (val result = getTrendingTvUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.TrendingTvLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.TrendingTvLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.TrendingTvLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetTrendingPeople(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.TrendingPeopleLoaded(MediaViewState(isLoading = true)))

            when (val result = getTrendingPeopleUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.TrendingPeopleLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.TrendingPeopleLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.TrendingPeopleLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        // ---------------------
        // Movies Handlers
        // ---------------------

        private suspend fun handleGetPopularMovies(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.PopularMoviesLoaded(MediaViewState(isLoading = true)))

            when (val result = getPopularMoviesUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.PopularMoviesLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.PopularMoviesLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.PopularMoviesLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetTopRatedMovies(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.TopRatedMoviesLoaded(MediaViewState(isLoading = true)))

            when (val result = getTopRatedMoviesUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.TopRatedMoviesLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.TopRatedMoviesLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.TopRatedMoviesLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetUpComingMovies(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.UpComingMoviesLoaded(MediaViewState(isLoading = true)))

            when (val result = getUpComingMoviesUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.UpComingMoviesLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.UpComingMoviesLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.UpComingMoviesLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        // ---------------------
        // TV Handlers
        // ---------------------

        private suspend fun handleGetOnTheAirTv(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.OnTheAirTvLoaded(MediaViewState(isLoading = true)))

            when (val result = getOnTheAirTvUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.OnTheAirTvLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.OnTheAirTvLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.OnTheAirTvLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetPopularTv(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.PopularTvLoaded(MediaViewState(isLoading = true)))

            when (val result = getPopularTvUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.PopularTvLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.PopularTvLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.PopularTvLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }

        private suspend fun handleGetTopRatedTv(flowCollector: FlowCollector<HomeResult>) {
            flowCollector.emit(HomeResult.TopRatedTvLoaded(MediaViewState(isLoading = true)))

            when (val result = getTopRatedTvUseCase(1)) {
                is DataState.Success ->
                    flowCollector.emit(
                        HomeResult.TopRatedTvLoaded(MediaViewState(data = result.data, isSuccess = true)),
                    )
                is DataState.Error ->
                    flowCollector.emit(
                        HomeResult.TopRatedTvLoaded(MediaViewState(errorThrowable = result.throwable)),
                    )
                is DataState.Empty ->
                    flowCollector.emit(
                        HomeResult.TopRatedTvLoaded(MediaViewState(isEmpty = true)),
                    )
                else -> {}
            }
        }
    }
