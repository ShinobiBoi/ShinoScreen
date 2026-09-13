package com.besha.shinobihub.features.detail.presentaion.viewmodel

import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MediaViewState
import com.besha.shinobihub.appcore.mvi.ViewState
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.besha.shinobihub.features.detail.domain.constants.DetailTab
import com.besha.shinobihub.features.detail.domain.model.DetailMediaItem

data class DetailViewState(
    val selectedTab: CommonViewState<DetailTab> = CommonViewState(data = DetailTab.INFO),
    val detailMediaItem: CommonViewState<DetailMediaItem> = CommonViewState(),
    val videoList: CommonViewState<List<VideoItem>> = CommonViewState(),
    val credits: CommonViewState<CreditsResponse> = CommonViewState(),
    val peopleCredits: MediaViewState = CommonViewState(),
    val similar: MediaViewState = CommonViewState(),
    val review: CommonViewState<List<Review>> = CommonViewState(),
    val isLoading: Boolean = false,
    val isFavorite: CommonViewState<Boolean> = CommonViewState(),
    val isWatchlist: CommonViewState<Boolean> = CommonViewState(),
    val toggleCode: Int = 0,
    val sessionId: String? = null,
) : ViewState
