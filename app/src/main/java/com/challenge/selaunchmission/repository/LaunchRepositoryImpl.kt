package com.challenge.selaunchmission.repository

import com.challenge.selaunchmission.datasource.remote.ApolloDataSource
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchResult
import javax.inject.Inject

class LaunchRepositoryImpl @Inject constructor(
    private val apolloDataSource: ApolloDataSource,
) : LaunchRepository {

    override suspend fun getLaunches(pageSize: Int, after: String?): LaunchResult? =
        apolloDataSource.getLaunches(pageSize, after)

    override suspend fun getLaunchInfo(launchId: String): Launch? =
        apolloDataSource.getLaunchInfo(launchId)
}
