package com.besha.shinobihub.features.detail.di

import com.besha.shinobihub.features.detail.data.remote.DetailRemoteClientImp
import com.besha.shinobihub.features.detail.data.repo.DetailRepoImp
import com.besha.shinobihub.features.detail.domain.remote.DetailRemoteClient
import com.besha.shinobihub.features.detail.domain.repo.DetailRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailBindModule {
    @Binds
    abstract fun bindDetailRemoteClient(detailRemoteClientImp: DetailRemoteClientImp): DetailRemoteClient

    @Binds
    abstract fun bindDetailRepo(detailRepoImp: DetailRepoImp): DetailRepo
}
