package com.besha.shinobihub.features.profile.domain.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.profile.data.model.DeleteSessionRequest

interface ProfileRepo {
    suspend fun deleteSession(body: DeleteSessionRequest): DataState<Boolean>
}
