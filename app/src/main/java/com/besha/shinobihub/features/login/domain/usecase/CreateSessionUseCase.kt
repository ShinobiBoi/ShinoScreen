package com.besha.shinobihub.features.login.domain.usecase

import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.domain.repo.LoginRepo
import javax.inject.Inject

class CreateSessionUseCase
    @Inject
    constructor(private val loginRepo: LoginRepo) {
        suspend operator fun invoke(sessionRequest: SessionRequest) = loginRepo.createSession(sessionRequest)
    }
