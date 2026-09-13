package com.besha.shinobihub.features.find.presenation.viewmodel

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.ViewState

data class FindViewState(
    val genres: CommonViewState<List<Genre>> = CommonViewState(),
    val mediaType: MediaType = MediaType.All,
    val query: CommonViewState<String> = CommonViewState(data = ""),
    val media: MediaViewState = CommonViewState(),
) : ViewState
