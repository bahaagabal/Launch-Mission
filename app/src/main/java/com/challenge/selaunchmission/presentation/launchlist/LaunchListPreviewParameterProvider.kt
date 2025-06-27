package com.challenge.selaunchmission.presentation.launchlist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.challenge.selaunchmission.domain.models.LaunchSnippet

class LaunchListPreviewParameterProvider : PreviewParameterProvider<LaunchListViewState> {

    private val launchListViewState: LaunchListViewState = LaunchListViewState(
        viewState = LaunchListState.SHOW_LIST,
        launches = listOf(
            LaunchSnippet(
                id = "110",
                missionName = "CRS-21",
                missionPatch = "https://imgur.com/E7fjUBD.png",
                rocketName = "Falcon 9"
            ),
            LaunchSnippet(
                id = "109",
                missionName = "Starlink-15 (v1.0)",
                missionPatch = "https://images2.imgbox.com/d2/3b/bQaWiil0_o.png",
                rocketName = "Falcon 9"
            ),
            LaunchSnippet(
                id = "108",
                missionName = "Sentinel-6 Michael Freilich",
                missionPatch = "",
                rocketName = "Falcon 9"
            )
        ),
        canLoadMore = true,
    )

    override val values: Sequence<LaunchListViewState> = sequenceOf(
        launchListViewState,
        launchListViewState.copy(viewState = LaunchListState.PAGE_LOADING),
        launchListViewState.copy(viewState = LaunchListState.PAGE_ERROR),
        LaunchListViewState(
            viewState = LaunchListState.ERROR,
            launches = emptyList(),
        ),
    )
}
