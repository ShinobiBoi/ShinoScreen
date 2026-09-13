package com.besha.shinobihub.features.detail.data.model.review

data class Review(
    val author: String? = "",
    val author_details: AuthorDetails? = AuthorDetails(),
    val content: String? = "",
    val created_at: String? = "",
    val id: String? = "",
    val updated_at: String? = "",
    val url: String? = "",
)
