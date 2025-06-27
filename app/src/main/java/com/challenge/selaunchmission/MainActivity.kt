package com.challenge.selaunchmission

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.challenge.selaunchmission.presentation.router.Screen
import com.challenge.selaunchmission.presentation.router.launchDetailScreen
import com.challenge.selaunchmission.presentation.router.launchListScreen
import com.challenge.selaunchmission.ui.theme.LaunchMissionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchMissionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LaunchMissionRoot()
                }
            }
        }
    }
}

@Composable
fun LaunchMissionRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LaunchList,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        launchListScreen(
            onNavigateToLaunchDetail = { launchId ->
                navController.navigate(Screen.LaunchDetail(launchId = launchId))
            }
        )

        launchDetailScreen(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
