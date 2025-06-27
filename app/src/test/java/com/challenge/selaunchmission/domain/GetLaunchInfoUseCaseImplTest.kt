package com.challenge.selaunchmission.domain

import com.challenge.selaunchmission.common.MainCoroutineScopeRule
import com.challenge.selaunchmission.repository.LaunchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetLaunchInfoUseCaseImplTest {

    @get:Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val launchRepository: LaunchRepository = mockk(relaxed = true)
    private lateinit var getLaunchInfoUseCase: GetLaunchInfoUseCaseImpl

    @Before
    fun setup() {
        getLaunchInfoUseCase = GetLaunchInfoUseCaseImpl(launchRepository)
    }

    @Test(expected = Test.None::class)
    fun `No exception when getting launch information from the repository`() = runTest {
        // Act
        getLaunchInfoUseCase.execute("110")
        // Assert
        coVerify { launchRepository.getLaunchInfo("110") }
    }
}
