package com.besha.shinobihub.features.discover.domain.usecase

import com.besha.shinobihub.features.discover.domain.repo.DiscoverRepo
import javax.inject.Inject

class GetDiscoverMovieUseCase
    @Inject
    constructor(private val discoverRepo: DiscoverRepo) {
        suspend operator fun invoke(genreId: String) = discoverRepo.getMovieDiscover(genreId)
    }
