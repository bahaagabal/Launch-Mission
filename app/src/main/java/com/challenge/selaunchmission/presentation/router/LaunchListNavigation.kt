package com.challenge.selaunchmission.presentation.router

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.challenge.selaunchmission.presentation.launchlist.LaunchListScreen
import com.challenge.selaunchmission.presentation.launchlist.LaunchListSideEffect
import com.challenge.selaunchmission.presentation.launchlist.LaunchListViewModel

fun NavGraphBuilder.launchListScreen(
    onNavigateToLaunchDetail: (launchId: String) -> Unit,
) {
    composable<Screen.LaunchList> {
        val launchListViewModel: LaunchListViewModel = hiltViewModel()
        val state by launchListViewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            launchListViewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is LaunchListSideEffect.OpenLaunchDetails -> {
                        onNavigateToLaunchDetail(sideEffect.launchId)
                    }
                }
            }
        }

        LaunchListScreen(
            viewState = state,
            onEvent = { launchListViewModel.onEvent(it) },
        )
    }
}
