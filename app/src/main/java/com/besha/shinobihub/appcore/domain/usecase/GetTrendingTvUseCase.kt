package com.besha.shinobihub.appcore.domain.usecase

import com.besha.shinobihub.appcore.domain.repo.SharedRepo
import javax.inject.Inject

class GetTrendingTvUseCase
    @Inject
    constructor(private val sharedRepo: SharedRepo) {
        suspend operator fun invoke(page: Int) = sharedRepo.getTrendingTv(page)
    }
