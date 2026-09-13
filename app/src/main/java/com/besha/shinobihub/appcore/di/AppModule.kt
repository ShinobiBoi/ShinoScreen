package com.besha.shinobihub.appcore.di

import android.content.Context
import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.data.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiServices(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideSessionManager(
        @ApplicationContext context: Context,
    ): SessionManager {
        return SessionManager(context)
    }
}
