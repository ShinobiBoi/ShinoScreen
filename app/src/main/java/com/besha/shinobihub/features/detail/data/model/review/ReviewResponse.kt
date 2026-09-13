package com.besha.shinobihub.features.detail.data.model.review

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    val id: Int? = 0,
    val page: Int? = 0,
    @SerializedName("results")
    val reviews: List<Review>? = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0,
)
