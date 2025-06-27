package com.challenge.selaunchmission.repository

import com.challenge.selaunchmission.datasource.remote.ApolloDataSource
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LaunchRepositoryImplTest {

    private val apolloDataSource: ApolloDataSource = mockk(relaxed = true)
    private lateinit var launchRepository: LaunchRepositoryImpl

    @Before
    fun setup() {
        launchRepository = LaunchRepositoryImpl(apolloDataSource)
    }

    @Test(expected = Test.None::class)
    fun `No exception when getting launches from the repository`() = runTest {
        // Act
        launchRepository.getLaunches(5, null)
        // Assert
        coVerify { apolloDataSource.getLaunches(5, null) }
    }

    @Test(expected = Test.None::class)
    fun `No exception when getting launch information from the repository`() = runTest {
        // Act
        launchRepository.getLaunchInfo("110")
        // Assert
        coVerify { apolloDataSource.getLaunchInfo("110") }
    }
}
