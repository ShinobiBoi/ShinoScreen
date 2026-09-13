package com.besha.shinobihub.features.detail.domain.usecase

import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import javax.inject.Inject

class GetPeopleCreditsUseCase
    @Inject
    constructor(private val detailRepo: DetailRepo) {
        suspend operator fun invoke(personId: Int) = detailRepo.getPeopleCredits(personId)
    }
