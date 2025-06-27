package com.challenge.selaunchmission.presentation.router

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object LaunchList : Screen()

    @Serializable
    data class LaunchDetail(val launchId: String) : Screen()
}
