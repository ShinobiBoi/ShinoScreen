package com.besha.shinobihub.features.login.di

import com.besha.shinobihub.features.login.data.remote.LoginRemoteClientImp
import com.besha.shinobihub.features.login.data.repo.LoginRepoImp
import com.besha.shinobihub.features.login.domain.remote.LoginRemoteClient
import com.besha.shinobihub.features.login.domain.repo.LoginRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginBindModule {
    @Binds
    abstract fun bindLoginRepo(loginRepoImp: LoginRepoImp): LoginRepo

    @Binds
    abstract fun bindLoginRemoteClient(loginRemoteClientImp: LoginRemoteClientImp): LoginRemoteClient
}
