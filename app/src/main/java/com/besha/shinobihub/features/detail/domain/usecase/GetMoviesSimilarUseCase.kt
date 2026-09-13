package com.besha.shinobihub.features.detail.domain.usecase

import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import javax.inject.Inject

class GetMoviesSimilarUseCase
    @Inject
    constructor(private val detailRepo: DetailRepo) {
        suspend operator fun invoke(movieId: Int) = detailRepo.getMovieSimilar(movieId)
    }
