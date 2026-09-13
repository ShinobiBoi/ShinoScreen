package com.besha.shinobihub.features.home.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.Result
import com.besha.shinobihub.features.home.data.model.account.AccountResponse

sealed class HomeResult : Result<HomeViewState> {
    data class Type(val state: CommonViewState<MediaType>) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                mediaType = state,
            )
        }
    }

    data class TrendingAllLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                trendingAll = state,
            )
        }
    }

    data class TrendingMoviesLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                trendingMovies = state,
            )
        }
    }

    data class TrendingTvLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                trendingTv = state,
            )
        }
    }

    data class TrendingPeopleLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                trendingPeople = state,
            )
        }
    }

    data class PopularMoviesLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                popularMovies = state,
            )
        }
    }

    data class TopRatedMoviesLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                topRatedMovies = state,
            )
        }
    }

    data class UpComingMoviesLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                upComingMovies = state,
            )
        }
    }

    data class OnTheAirTvLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                onTheAirTv = state,
            )
        }
    }

    data class PopularTvLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                popularTv = state,
            )
        }
    }

    data class TopRatedTvLoaded(val state: MediaViewState) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                topRatedTv = state,
            )
        }
    }

    data class AccountedLoaded(val state: CommonViewState<AccountResponse>) : HomeResult() {
        override fun reduce(
            defaultState: HomeViewState,
            oldState: HomeViewState,
        ): HomeViewState {
            return oldState.copy(
                account = state,
            )
        }
    }
}
