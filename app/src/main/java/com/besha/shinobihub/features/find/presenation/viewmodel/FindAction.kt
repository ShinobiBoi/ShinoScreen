package com.besha.shinobihub.features.find.presenation.viewmodel

import com.besha.shinobihub.appcore.data.model.genre.Genre
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.appcore.mvi.Action

sealed class FindAction() : Action {
    data class ChangeMediaType(val type: MediaType) : FindAction()

    data class ChangeQuery(val query: String) : FindAction()

    data class SearchMulti(val query: String, val filterList: List<Genre>) : FindAction()

    data class SearchMovie(val query: String, val filterList: List<Genre>) : FindAction()

    data class SearchTv(val query: String, val filterList: List<Genre>) : FindAction()

    data class SearchPeople(val query: String, val filterList: List<Genre>) : FindAction()

    data class GetTrendingAll(val filterList: List<Genre>) : FindAction()

    data class GetTrendingMovies(val filterList: List<Genre>) : FindAction()

    data class GetTrendingTv(val filterList: List<Genre>) : FindAction()

    data class GetTrendingPeople(val filterList: List<Genre>) : FindAction()

    object GetGenreList : FindAction()

    data class ToggleGenre(val genre: Genre) : FindAction()

    object ClearFilters : FindAction()
}
