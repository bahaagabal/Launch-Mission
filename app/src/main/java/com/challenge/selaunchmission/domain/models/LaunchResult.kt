package com.challenge.selaunchmission.domain.models

data class LaunchResult(
    val launches: List<LaunchSnippet>,
    val canLoadMore: Boolean,
)
