package com.besha.shinobihub.features.discover.domain.usecase

import com.besha.shinobihub.features.discover.domain.repo.DiscoverRepo
import javax.inject.Inject

class
GetDiscoverTvUseCase@Inject
    constructor(private val discoverRepo: DiscoverRepo) {
        suspend operator fun invoke(genreId: String) = discoverRepo.getTvDiscover(genreId)
    }
