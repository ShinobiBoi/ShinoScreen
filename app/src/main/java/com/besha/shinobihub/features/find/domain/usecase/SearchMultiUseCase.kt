package com.besha.shinobihub.features.find.domain.usecase

import com.besha.shinobihub.features.find.domain.repo.FindRepo
import javax.inject.Inject

class SearchMultiUseCase
    @Inject
    constructor(private val findRepo: FindRepo) {
        suspend operator fun invoke(
            query: String,
            page: Int,
        ) = findRepo.searchMulti(query, page)
    }
