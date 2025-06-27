package com.challenge.selaunchmission.presentation.launchdetail

sealed class LaunchDetailEvent {
    data class OnScreenLoaded(val launchId: String) : LaunchDetailEvent()
    data class Reload(val launchId: String) : LaunchDetailEvent()
    data object Close : LaunchDetailEvent()
}
