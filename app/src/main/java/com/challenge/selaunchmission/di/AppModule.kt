package com.challenge.selaunchmission.di

import com.apollographql.apollo3.ApolloClient
import com.challenge.selaunchmission.datasource.remote.ApolloDataSource
import com.challenge.selaunchmission.datasource.remote.ApolloDataSourceImpl
import com.challenge.selaunchmission.domain.GetLaunchInfoUseCase
import com.challenge.selaunchmission.domain.GetLaunchInfoUseCaseImpl
import com.challenge.selaunchmission.domain.GetLaunchesUseCase
import com.challenge.selaunchmission.domain.GetLaunchesUseCaseImpl
import com.challenge.selaunchmission.repository.LaunchRepository
import com.challenge.selaunchmission.repository.LaunchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideGetLaunchesUseCase(
        launchRepository: LaunchRepository,
    ): GetLaunchesUseCase =
        GetLaunchesUseCaseImpl(launchRepository)

    @Provides
    fun provideGetLaunchInfoUseCase(
        launchRepository: LaunchRepository,
    ): GetLaunchInfoUseCase =
        GetLaunchInfoUseCaseImpl(launchRepository)

    @Provides
    fun provideApolloDataSource(
        apolloClient: ApolloClient,
    ): ApolloDataSource =
        ApolloDataSourceImpl(apolloClient)

    @Provides
    fun provideLaunchRepository(
        apolloDataSource: ApolloDataSource,
    ): LaunchRepository =
        LaunchRepositoryImpl(apolloDataSource)
}
