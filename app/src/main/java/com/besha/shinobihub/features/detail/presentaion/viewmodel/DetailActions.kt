package com.besha.shinobihub.features.detail.presentaion.viewmodel

import com.besha.shinobihub.appcore.mvi.Action
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.domain.constants.DetailTab

sealed class DetailActions : Action {
    data class SwitchTab(val tab: DetailTab) : DetailActions()

    data class GetDetailMovie(val movieId: Int) : DetailActions()

    data class GetDetailTv(val seriesId: Int) : DetailActions()

    data class GetDetailPerson(val personId: Int) : DetailActions()

    data class GetMovieVideo(val movieId: Int) : DetailActions()

    data class GetTvVideo(val seriesId: Int) : DetailActions()

    data class GetMovieCredits(val movieId: Int) : DetailActions()

    data class GetTvCredits(val seriesId: Int) : DetailActions()

    data class GetPeopleCredits(val personId: Int) : DetailActions()

    data class GetMovieSimilar(val movieId: Int) : DetailActions()

    data class GetTvSimilar(val seriesId: Int) : DetailActions()

    data class GetMovieReviews(val movieId: Int) : DetailActions()

    data class GetTvReviews(val seriesId: Int) : DetailActions()

    data class GetMovieAccountState(val movieId: Int) : DetailActions()

    data class GetTvAccountState(val tvId: Int) : DetailActions()

    data class ToggleFavorite(val markRequest: MarkRequest) : DetailActions()

    data class ToggleWatchList(val markRequest: MarkRequest) : DetailActions()

    object ResetToggleCode : DetailActions()

    object GetSessionId : DetailActions()
}
