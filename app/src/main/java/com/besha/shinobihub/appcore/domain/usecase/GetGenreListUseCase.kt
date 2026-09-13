package com.besha.shinobihub.appcore.domain.usecase

import com.besha.shinobihub.appcore.domain.repo.SharedRepo
import javax.inject.Inject

class GetGenreListUseCase
    @Inject
    constructor(private val sharedRepo: SharedRepo) {
        suspend operator fun invoke() = sharedRepo.getGenreList()
    }
