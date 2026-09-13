package com.besha.shinobihub.features.home.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.Action

sealed class HomeAction : Action {
    data class ChangeMediaType(val type: MediaType) : HomeAction()

    object GetTrendingAll : HomeAction()

    object GetTrendingMovies : HomeAction()

    object GetTrendingTv : HomeAction()

    object GetTrendingPeople : HomeAction()

    object GetPopularMovies : HomeAction()

    object GetTopRatedMovies : HomeAction()

    object GetUpComingMovies : HomeAction()

    object GetOnTheAirTv : HomeAction()

    object GetPopularTv : HomeAction()

    object GetTopRatedTv : HomeAction()

    object GetAccount : HomeAction()
}
