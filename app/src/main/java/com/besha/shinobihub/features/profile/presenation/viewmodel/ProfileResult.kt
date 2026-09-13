package com.besha.shinobihub.features.profile.presenation.viewmodel

import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.Result
import com.besha.shinobihub.features.home.data.model.account.AccountResponse

sealed class ProfileResult() : Result<ProfileViewState> {
    data class AccountLoaded(val account: CommonViewState<AccountResponse>) : ProfileResult() {
        override fun reduce(
            defaultState: ProfileViewState,
            oldState: ProfileViewState,
        ): ProfileViewState {
            return oldState.copy(
                account = account,
            )
        }
    }

    data class NotificationToggle(val notification: Boolean) : ProfileResult() {
        override fun reduce(
            defaultState: ProfileViewState,
            oldState: ProfileViewState,
        ): ProfileViewState {
            return oldState.copy(
                notification = notification,
            )
        }
    }

    data class LoggedOut(val loggedOut: CommonViewState<Boolean>) : ProfileResult() {
        override fun reduce(
            defaultState: ProfileViewState,
            oldState: ProfileViewState,
        ): ProfileViewState {
            return oldState.copy(
                loggedOut = loggedOut,
            )
        }
    }
}
