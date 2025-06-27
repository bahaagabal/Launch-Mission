package com.challenge.selaunchmission.domain

import com.challenge.selaunchmission.common.MainCoroutineScopeRule
import com.challenge.selaunchmission.repository.LaunchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetLaunchesUseCaseImplTest {

    @get:Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val launchRepository: LaunchRepository = mockk(relaxed = true)
    private lateinit var getLaunchesUseCase: GetLaunchesUseCaseImpl

    @Before
    fun setup() {
        getLaunchesUseCase = GetLaunchesUseCaseImpl(launchRepository)
    }

    @Test(expected = Test.None::class)
    fun `No exception when getting launches from the repository`() = runTest {
        // Act
        getLaunchesUseCase.execute(5, null)
        // Assert
        coVerify { launchRepository.getLaunches(5, null) }
    }
}
