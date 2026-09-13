package com.besha.shinobihub.features.home.di

import com.besha.shinobihub.features.home.data.remote.HomeRemoteClientImp
import com.besha.shinobihub.features.home.data.repo.HomeRepoImp
import com.besha.shinobihub.features.home.domain.remote.HomeRemoteClient
import com.besha.shinobihub.features.home.domain.repo.HomeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeBindModule {
    @Binds
    abstract fun bindHomeRemoteClient(homeRemoteClientImp: HomeRemoteClientImp): HomeRemoteClient

    @Binds
    abstract fun bindHomeRepo(homeRepoImp: HomeRepoImp): HomeRepo
}
