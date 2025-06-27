package com.challenge.selaunchmission.presentation.launchlist

import com.challenge.selaunchmission.domain.models.LaunchSnippet

sealed class LaunchListEvent {
    data object Reload: LaunchListEvent()
    data class SelectLaunch(val launch: LaunchSnippet): LaunchListEvent()
    data object LoadMore: LaunchListEvent()
}
