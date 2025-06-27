package com.challenge.selaunchmission.presentation.launchlist

import app.cash.turbine.test
import com.challenge.selaunchmission.domain.GetLaunchesUseCase
import com.challenge.selaunchmission.domain.models.LaunchResult
import com.challenge.selaunchmission.domain.models.LaunchSnippet
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getLaunchesUseCase: GetLaunchesUseCase
    private lateinit var viewModel: LaunchListViewModel

    private val fakeLaunch = LaunchSnippet(
        id = "abc123",
        missionName = "Test Mission",
        missionPatch = "patch.png",
        rocketName = "Falcon 9"
    )

    private val extraLaunch = LaunchSnippet(
        id = "abc124",
        missionName = "Second Mission",
        missionPatch = "patch2.png",
        rocketName = "Falcon Heavy"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getLaunchesUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadInitialLaunches should emit loading then show list state`() = runTest {
        val firstPageResult = LaunchResult(
            launches = listOf(fakeLaunch),
            canLoadMore = true
        )
        coEvery { getLaunchesUseCase.execute(any(), any()) } returns firstPageResult

        viewModel = LaunchListViewModel(getLaunchesUseCase)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertEquals(LaunchListState.LOADING, loadingState.viewState)

            val loadedState = awaitItem()
            assertEquals(LaunchListState.SHOW_LIST, loadedState.viewState)
            assertEquals(1, loadedState.launches.size)
            assertEquals("Test Mission", loadedState.launches.first().missionName)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadInitialLaunches should emit error state when result is null`() = runTest {
        coEvery { getLaunchesUseCase.execute(any(), any()) } returns null

        viewModel = LaunchListViewModel(getLaunchesUseCase)

        viewModel.state.test {
            assertEquals(LaunchListState.LOADING, awaitItem().viewState)
            assertEquals(LaunchListState.ERROR, awaitItem().viewState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent LoadMore should emit page loading then append launches`() = runTest {
        val firstPageResult = LaunchResult(
            launches = listOf(fakeLaunch),
            canLoadMore = true
        )
        val secondPageResult = LaunchResult(
            launches = listOf(extraLaunch),
            canLoadMore = false
        )

        coEvery { getLaunchesUseCase.execute(15, null) } returns firstPageResult
        coEvery { getLaunchesUseCase.execute(15, "abc123") } returns secondPageResult

        viewModel = LaunchListViewModel(getLaunchesUseCase)

        viewModel.state.test {
            skipItems(2)

            launch {
                viewModel.onEvent(LaunchListEvent.LoadMore)
            }

            val maybeLoading = awaitItem()
            if (maybeLoading.viewState == LaunchListState.PAGE_LOADING) {
                val loaded = awaitItem()
                assertEquals(LaunchListState.SHOW_LIST, loaded.viewState)
                assertEquals(2, loaded.launches.size)
                assertEquals("abc123", loaded.launches[1].id)
            } else {
                fail("Expected PAGE_LOADING but got ${maybeLoading.viewState}")
            }

            cancelAndIgnoreRemainingEvents()
        }
    }



    @Test
    fun `onEvent SelectLaunch should emit OpenLaunchDetails side effect`() = runTest {
        coEvery { getLaunchesUseCase.execute(any(), any()) } returns LaunchResult(
            launches = listOf(fakeLaunch),
            canLoadMore = true
        )

        viewModel = LaunchListViewModel(getLaunchesUseCase)

        viewModel.sideEffect.test {
            viewModel.onEvent(LaunchListEvent.SelectLaunch(fakeLaunch))

            val effect = awaitItem()
            assertTrue(effect is LaunchListSideEffect.OpenLaunchDetails)
            assertEquals("abc123", (effect as LaunchListSideEffect.OpenLaunchDetails).launchId)

            cancelAndIgnoreRemainingEvents()
        }
    }
}