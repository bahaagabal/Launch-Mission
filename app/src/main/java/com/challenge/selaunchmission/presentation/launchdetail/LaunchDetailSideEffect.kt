package com.challenge.selaunchmission.presentation.launchdetail

sealed class LaunchDetailSideEffect {
    data object Close : LaunchDetailSideEffect()
}
