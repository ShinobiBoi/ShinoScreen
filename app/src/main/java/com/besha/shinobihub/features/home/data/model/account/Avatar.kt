package com.besha.shinobihub.features.home.data.model.account

data class Avatar(
    val gravatar: Gravatar? = Gravatar(),
    val tmdb: Tmdb? = Tmdb(),
)
