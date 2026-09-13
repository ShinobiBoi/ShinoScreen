package com.besha.shinobihub.features.detail.domain.usecase

import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import javax.inject.Inject

class GetTvReviewsUseCase
    @Inject
    constructor(private val detailRepo: DetailRepo) {
        suspend operator fun invoke(seriesId: Int) = detailRepo.getTvReview(seriesId)
    }
