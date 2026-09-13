package com.besha.shinobihub.features.discover.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.ViewState

data class DiscoverViewState(
    val genres: CommonViewState<List<Genre>> = CommonViewState(),
    val media: MediaViewState = CommonViewState(),
    val mediaType: MediaType = MediaType.Movies,
    val isLoading: Boolean = false,
) : ViewState
