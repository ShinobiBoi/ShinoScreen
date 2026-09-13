package com.besha.shinobihub.features.home.domain.usecase

import com.besha.shinobihub.features.home.domain.repo.HomeRepo
import javax.inject.Inject

class GetUpComingMoviesUseCase
    @Inject
    constructor(private val homeRepo: HomeRepo) {
        suspend operator fun invoke(page: Int) = homeRepo.getUpComingMovies(page)
    }
