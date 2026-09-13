package com.besha.shinobihub.features.login.domain.usecase

import com.besha.shinobihub.features.login.domain.repo.LoginRepo
import javax.inject.Inject

class CreateTokenUseCase
    @Inject
    constructor(private val loginRepo: LoginRepo) {
        suspend operator fun invoke() = loginRepo.createToken()
    }
