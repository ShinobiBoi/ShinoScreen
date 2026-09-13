package com.besha.shinobihub.features.discover.di

import com.besha.shinobihub.features.discover.data.remote.DiscoverRemoteClientImp
import com.besha.shinobihub.features.discover.data.repo.DiscoverRepoImp
import com.besha.shinobihub.features.discover.domain.remote.DiscoverRemoteClient
import com.besha.shinobihub.features.discover.domain.repo.DiscoverRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DiscoverBindModule {
    @Binds
    abstract fun bindDiscoverRemote(discoverRemoteClientImp: DiscoverRemoteClientImp): DiscoverRemoteClient

    @Binds
    abstract fun bindDiscoverRepo(discoverRepoImp: DiscoverRepoImp): DiscoverRepo
}
