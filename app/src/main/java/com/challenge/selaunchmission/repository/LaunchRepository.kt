package com.challenge.selaunchmission.repository

import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchResult

interface LaunchRepository {
    suspend fun getLaunches(pageSize: Int, after: String?): LaunchResult?
    suspend fun getLaunchInfo(launchId: String): Launch?
}
