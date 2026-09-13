package com.besha.shinobihub.features.profile.domain.remote

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.login.data.model.session.SessionResponse
import com.besha.shinobihub.features.profile.data.model.DeleteSessionRequest

interface ProfileRemoteClient {
    suspend fun deleteSession(body: DeleteSessionRequest): DataState<SessionResponse>
}
