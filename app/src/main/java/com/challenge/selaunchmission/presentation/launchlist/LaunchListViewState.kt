package com.challenge.selaunchmission.presentation.launchlist

import com.challenge.selaunchmission.domain.models.LaunchSnippet

data class LaunchListViewState(
    val viewState: LaunchListState = LaunchListState.LOADING,
    val launches: List<LaunchSnippet> = emptyList(),
    val canLoadMore: Boolean = false,
)
