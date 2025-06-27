package com.challenge.selaunchmission.presentation.launchdetail

import com.challenge.selaunchmission.domain.models.Launch

data class LaunchDetailViewState(
    val viewState: LaunchDetailState = LaunchDetailState.EMPTY,
    val launch: Launch? = null,
)
