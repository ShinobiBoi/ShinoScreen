package com.besha.shinobihub.features.profile.data.remote

import com.besha.shinobihub.appcore.data.remote.ApiServices
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.login.data.model.session.SessionResponse
import com.besha.shinobihub.features.profile.data.model.DeleteSessionRequest
import com.besha.shinobihub.features.profile.domain.remote.ProfileRemoteClient
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRemoteClientImp
    @Inject
    constructor(private val apiServices: ApiServices) :
    ProfileRemoteClient {
        override suspend fun deleteSession(body: DeleteSessionRequest): DataState<SessionResponse> {
            val result = apiServices.deleteSession(body)

            if (result.isSuccessful) {
                if (result.body() != null) {
                    return DataState.Success(result.body()!!)
                }
                return DataState.Empty
            }
            return DataState.Error(HttpException(result))
        }
    }
