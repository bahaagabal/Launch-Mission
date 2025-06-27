package com.challenge.selaunchmission.domain

import com.challenge.selaunchmission.domain.models.LaunchResult
import com.challenge.selaunchmission.repository.LaunchRepository
import javax.inject.Inject

interface GetLaunchesUseCase {
    suspend fun execute(pageSize: Int, after: String?): LaunchResult?
}

class GetLaunchesUseCaseImpl @Inject constructor(
    private val launchRepository: LaunchRepository,
) : GetLaunchesUseCase {

    override suspend fun execute(pageSize: Int, after: String?): LaunchResult? =
        launchRepository.getLaunches(pageSize, after)
}
