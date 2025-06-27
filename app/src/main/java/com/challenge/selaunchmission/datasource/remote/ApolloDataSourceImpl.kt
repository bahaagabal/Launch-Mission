package com.challenge.selaunchmission.datasource.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.challenge.selaunchmission.GetLaunchInfoQuery
import com.challenge.selaunchmission.GetLaunchesQuery
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchResult
import com.challenge.selaunchmission.repository.toLaunch
import com.challenge.selaunchmission.repository.toLaunchSnippet
import javax.inject.Inject

class ApolloDataSourceImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : ApolloDataSource {

    override suspend fun getLaunches(pageSize: Int, after: String?): LaunchResult? {
        val data = apolloClient.query(GetLaunchesQuery(pageSize, Optional.presentIfNotNull(after)))
            .execute().data
        return if (data != null) {
            LaunchResult(
                launches = data.launches.launches.mapNotNull { it?.toLaunchSnippet() },
                canLoadMore = data.launches.hasMore,
            )
        } else null
    }

    override suspend fun getLaunchInfo(launchId: String): Launch? =
        apolloClient.query(GetLaunchInfoQuery(launchId)).execute().data?.launch?.toLaunch()
}
