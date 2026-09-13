package com.besha.shinobihub.appcore.domain.model

enum class MediaType(val value: String) {
    All(""),
    Movies("movie"),
    Tv("tv"),
    People("person"),
    ;

    companion object {
        operator fun invoke(value: String): MediaType = MediaType.entries.first { it.value == value }
    }
}
