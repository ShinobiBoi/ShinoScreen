package com.besha.shinobihub.appcore.domain.usecase

import com.besha.shinobihub.appcore.domain.repo.SharedRepo
import javax.inject.Inject

class GetTrendingAllUseCase
    @Inject
    constructor(private val sharedRepo: SharedRepo) {
        suspend operator fun invoke(page: Int) = sharedRepo.getTrendingAll(page)
    }
