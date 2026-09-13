package com.besha.shinobihub.features.detail.domain.usecase

import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import javax.inject.Inject

class ToggleFavoriteUseCase
    @Inject
    constructor(
        private val detailRepo: DetailRepo,
    ) {
        suspend operator fun invoke(
            accountId: Int,
            body: MarkRequest,
            sessionId: String,
        ) = detailRepo.toggleFavorite(accountId, body, sessionId)
    }
