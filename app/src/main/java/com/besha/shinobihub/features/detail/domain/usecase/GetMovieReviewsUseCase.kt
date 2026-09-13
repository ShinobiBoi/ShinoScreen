package com.besha.shinobihub.features.detail.domain.usecase

import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import javax.inject.Inject

class GetMovieReviewsUseCase
    @Inject
    constructor(private val detailRepo: DetailRepo) {
        suspend operator fun invoke(movieId: Int) = detailRepo.getMovieReview(movieId)
    }
