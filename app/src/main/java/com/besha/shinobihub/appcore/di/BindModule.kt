package com.besha.shinobihub.appcore.di

import com.besha.shinobihub.appcore.data.remote.SharedRemoteClientImp
import com.besha.shinobihub.appcore.data.repo.SharedRepoImp
import com.besha.shinobihub.appcore.domain.remote.SharedRemoteClient
import com.besha.shinobihub.appcore.domain.repo.SharedRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    abstract fun bindSharedRemoteClient(sharedRemoteClientImp: SharedRemoteClientImp): SharedRemoteClient

    @Binds
    abstract fun bindSharedRepo(sharedRepoImp: SharedRepoImp): SharedRepo
}
