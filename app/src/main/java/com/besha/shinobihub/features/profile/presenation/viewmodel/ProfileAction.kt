package com.besha.shinobihub.features.profile.presenation.viewmodel

import android.content.Context
import com.besha.shinobihub.appcore.mvi.Action

sealed class ProfileAction : Action {
    object GetAccount : ProfileAction()

    object LogOut : ProfileAction()

    data class GetNotifications(val context: Context) : ProfileAction()

    data class ToggleNotifications(val notification: Boolean, val context: Context) : ProfileAction()
}
