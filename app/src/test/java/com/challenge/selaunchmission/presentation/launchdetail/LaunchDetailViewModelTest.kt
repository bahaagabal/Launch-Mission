package com.challenge.selaunchmission.presentation.launchdetail

import app.cash.turbine.test
import com.challenge.selaunchmission.domain.GetLaunchInfoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.Mission
import com.challenge.selaunchmission.domain.models.Rocket
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getLaunchInfoUseCase: GetLaunchInfoUseCase
    private lateinit var viewModel: LaunchDetailViewModel

    private val fakeMission = Mission(
        name = "Starlink Mission",
        missionPatch = "patch_url.png"
    )

    private val fakeRocket = Rocket(
        id = "123",
        name = "Falcon 9",
        type = "FT"
    )

    private val fakeLaunch = Launch(
        id = "launch123",
        site = "LC-39A",
        mission = fakeMission,
        rocket = fakeRocket,
        isBooked = false
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getLaunchInfoUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnScreenLoaded event should load launch and update state to SHOW_DETAIL`() = runTest {
        coEvery { getLaunchInfoUseCase.execute("launch123") } returns fakeLaunch

        viewModel = LaunchDetailViewModel(getLaunchInfoUseCase)

        launch {
            viewModel.onEvent(LaunchDetailEvent.OnScreenLoaded("launch123"))
        }

        advanceUntilIdle()

        viewModel.state.test {
            val updated = awaitItem()
            assertEquals(LaunchDetailState.SHOW_DETAIL, updated.viewState)
            assertEquals("Starlink Mission", updated.launch?.mission?.name)
            assertEquals("Falcon 9", updated.launch?.rocket?.name)
            assertFalse(updated.launch?.isBooked ?: true)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnScreenLoaded event with null result should update state to ERROR`() = runTest {
        coEvery { getLaunchInfoUseCase.execute("launch123") } returns null

        viewModel = LaunchDetailViewModel(getLaunchInfoUseCase)

        launch {
            viewModel.onEvent(LaunchDetailEvent.OnScreenLoaded("launch123"))
        }

        advanceUntilIdle()

        viewModel.state.test {
            val updated = awaitItem()
            assertEquals(LaunchDetailState.ERROR, updated.viewState)
            assertNull(updated.launch)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Reload event should reload and update state to SHOW_DETAIL`() = runTest {
        coEvery { getLaunchInfoUseCase.execute("launch123") } returns fakeLaunch

        viewModel = LaunchDetailViewModel(getLaunchInfoUseCase)

        launch {
            viewModel.onEvent(LaunchDetailEvent.Reload("launch123"))
        }

        advanceUntilIdle()

        viewModel.state.test {
            val updated = awaitItem()
            assertEquals(LaunchDetailState.SHOW_DETAIL, updated.viewState)
            assertEquals("LC-39A", updated.launch?.site)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Close event should emit Close side effect`() = runTest {
        viewModel = LaunchDetailViewModel(getLaunchInfoUseCase)

        viewModel.sideEffect.test {
            launch {
                viewModel.onEvent(LaunchDetailEvent.Close)
            }

            val effect = awaitItem()
            assertEquals(LaunchDetailSideEffect.Close, effect)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
