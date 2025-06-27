package com.challenge.selaunchmission.datasource.remote

import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchResult

interface ApolloDataSource {
    suspend fun getLaunches(pageSize: Int, after: String?): LaunchResult?
    suspend fun getLaunchInfo(launchId: String): Launch?
}
