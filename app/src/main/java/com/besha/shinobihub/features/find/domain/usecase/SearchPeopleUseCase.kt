package com.besha.shinobihub.features.find.domain.usecase

import com.besha.shinobihub.features.find.domain.repo.FindRepo
import javax.inject.Inject

class SearchPeopleUseCase
    @Inject
    constructor(private val findRepo: FindRepo) {
        suspend operator fun invoke(
            query: String,
            page: Int,
        ) = findRepo.searchPeople(query, page)
    }
