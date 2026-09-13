package com.besha.shinobihub.features.watchlist.di

import com.besha.shinobihub.features.watchlist.data.remote.WatchListRemoteClientImp
import com.besha.shinobihub.features.watchlist.data.repo.WatchListRepoImp
import com.besha.shinobihub.features.watchlist.domain.remote.WatchListRemoteClient
import com.besha.shinobihub.features.watchlist.domain.repo.WatchListRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WatchListBindModule {
    @Binds
    abstract fun bindWatchListRemoteClient(client: WatchListRemoteClientImp): WatchListRemoteClient

    @Binds
    abstract fun bindWatchListRepo(repo: WatchListRepoImp): WatchListRepo
}
