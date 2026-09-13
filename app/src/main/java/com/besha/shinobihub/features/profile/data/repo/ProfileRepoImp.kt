package com.besha.shinobihub.features.profile.data.repo

import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.features.profile.data.model.DeleteSessionRequest
import com.besha.shinobihub.features.profile.domain.remote.ProfileRemoteClient
import com.besha.shinobihub.features.profile.domain.repo.ProfileRepo
import javax.inject.Inject

class ProfileRepoImp
    @Inject
    constructor(private val remote: ProfileRemoteClient) : ProfileRepo {
        override suspend fun deleteSession(body: DeleteSessionRequest): DataState<Boolean> {
            return when (val result = remote.deleteSession(body)) {
                is DataState.Success -> {
                    DataState.Success(result.data.success)
                }
                is DataState.Error -> {
                    DataState.Error(result.throwable)
                }
                else -> {
                    DataState.Error(UnknownError())
                }
            }
        }
    }
