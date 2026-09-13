package com.besha.shinobihub.features.favourite.domain.usecase

import com.besha.shinobihub.features.favourite.domain.repo.FavouriteRepo
import javax.inject.Inject

class GetMovieFavouriteUseCase
    @Inject
    constructor(private val favouriteRepo: FavouriteRepo) {
        suspend operator fun invoke(
            accountId: Int,
            sessionId: String,
        ) = favouriteRepo.getMovieFavourite(accountId, sessionId)
    }
