package com.besha.shinobihub.features.profile.domain.usecase

import com.besha.shinobihub.features.profile.data.model.DeleteSessionRequest
import com.besha.shinobihub.features.profile.domain.repo.ProfileRepo
import javax.inject.Inject

class DeleteSessionUseCase
    @Inject
    constructor(private val profileRepo: ProfileRepo) {
        suspend operator fun invoke(body: DeleteSessionRequest) = profileRepo.deleteSession(body)
    }
