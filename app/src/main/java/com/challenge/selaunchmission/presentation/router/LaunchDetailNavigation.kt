package com.challenge.selaunchmission.presentation.router

import android.content.pm.ActivityInfo
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.challenge.selaunchmission.presentation.common.compose.LockScreenOrientation
import com.challenge.selaunchmission.presentation.launchdetail.LaunchDetailScreen
import com.challenge.selaunchmission.presentation.launchdetail.LaunchDetailSideEffect
import com.challenge.selaunchmission.presentation.launchdetail.LaunchDetailViewModel

fun NavGraphBuilder.launchDetailScreen(
    onNavigateBack: () -> Unit,
) {
    composable<Screen.LaunchDetail> { backStackEntry ->
        val launchDetailScreen = backStackEntry.toRoute<Screen.LaunchDetail>()
        val launchDetailViewModel: LaunchDetailViewModel = hiltViewModel()
        val state by launchDetailViewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = launchDetailViewModel) {
            launchDetailViewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    LaunchDetailSideEffect.Close -> onNavigateBack()
                }
            }
        }

        LaunchDetailScreen(
            launchId = launchDetailScreen.launchId,
            viewState = state,
            onEvent = { launchDetailViewModel.onEvent(it) },
        )
    }
}
