package com.besha.shinobihub.features.detail.data.model.credits

data class CreditsResponse(
    val cast: List<Cast?>? = listOf(),
    val crew: List<Crew?>? = listOf(),
    val id: Int? = 0,
)
