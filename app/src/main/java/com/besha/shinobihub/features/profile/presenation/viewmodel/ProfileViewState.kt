package com.besha.shinobihub.features.profile.presenation.viewmodel

import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.ViewState
import com.besha.shinobihub.features.home.data.model.account.AccountResponse

data class ProfileViewState(
    val account: CommonViewState<AccountResponse> = CommonViewState(),
    val loggedOut: CommonViewState<Boolean> = CommonViewState(),
    val notification: Boolean = false,
) : ViewState
