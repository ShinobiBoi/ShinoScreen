package com.besha.shinobihub.features.login.domain.usecase

import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.domain.repo.LoginRepo
import javax.inject.Inject

class LoginUseCase
    @Inject
    constructor(private val loginRepo: LoginRepo) {
        suspend operator fun invoke(loginRequest: LoginRequest) = loginRepo.login(loginRequest)
    }
