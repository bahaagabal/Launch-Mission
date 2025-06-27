package com.challenge.selaunchmission.di

import com.apollographql.apollo3.ApolloClient
import com.challenge.selaunchmission.datasource.remote.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideApolloClient(): ApolloClient =
        ApolloClient.Builder()
            .serverUrl(NetworkConstants.BASE_URL)
            .build()
}
