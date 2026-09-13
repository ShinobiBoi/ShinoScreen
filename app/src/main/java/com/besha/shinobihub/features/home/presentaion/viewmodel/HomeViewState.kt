package com.besha.shinobihub.features.home.presentaion.viewmodel

import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.ViewState
import com.besha.shinobihub.features.home.data.model.account.AccountResponse

data class HomeViewState(
    val mediaType: CommonViewState<MediaType> = CommonViewState(data = MediaType.All),
    val trendingAll: MediaViewState = CommonViewState(),
    val trendingMovies: MediaViewState = CommonViewState(),
    val trendingTv: MediaViewState = CommonViewState(),
    val trendingPeople: MediaViewState = CommonViewState(),
    val popularMovies: MediaViewState = CommonViewState(),
    val topRatedMovies: MediaViewState = CommonViewState(),
    val upComingMovies: MediaViewState = CommonViewState(),
    val onTheAirTv: MediaViewState = CommonViewState(),
    val popularTv: MediaViewState = CommonViewState(),
    val topRatedTv: MediaViewState = CommonViewState(),
    val account: CommonViewState<AccountResponse> = CommonViewState(),
) : ViewState
