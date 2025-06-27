package com.challenge.selaunchmission.presentation.launchlist

sealed class LaunchListSideEffect {
    data class OpenLaunchDetails(val launchId: String): LaunchListSideEffect()
}
