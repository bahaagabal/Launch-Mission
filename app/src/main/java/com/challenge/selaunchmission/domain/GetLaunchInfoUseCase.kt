package com.challenge.selaunchmission.domain

import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.repository.LaunchRepository
import javax.inject.Inject

interface GetLaunchInfoUseCase {
    suspend fun execute(launchId: String): Launch?
}

class GetLaunchInfoUseCaseImpl @Inject constructor(
    private val launchRepository: LaunchRepository,
) : GetLaunchInfoUseCase {

    override suspend fun execute(launchId: String): Launch? {
        return launchRepository.getLaunchInfo(launchId)
    }
}
