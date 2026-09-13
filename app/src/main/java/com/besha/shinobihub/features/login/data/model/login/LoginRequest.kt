package com.besha.shinobihub.features.login.data.model.login

data class LoginRequest(
    val username: String,
    val password: String,
    val request_token: String,
)
