package com.challenge.selaunchmission.presentation.launchdetail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.Mission
import com.challenge.selaunchmission.domain.models.Rocket

class LaunchDetailPreviewParameterProvider : PreviewParameterProvider<LaunchDetailViewState> {

    override val values: Sequence<LaunchDetailViewState> = sequenceOf(
        LaunchDetailViewState(
            viewState = LaunchDetailState.SHOW_DETAIL,
            launch = Launch(
                id = "110",
                site = "KSC LC 39A",
                mission = Mission(
                    name = "CRS-21",
                    missionPatch = "https://imgur.com/E7fjUBD.png"
                ),
                rocket = Rocket(
                    id = "falcon9",
                    name = "Falcon 9",
                    type = "FT",
                ),
                isBooked = false
            ),
        ),
        LaunchDetailViewState(
            viewState = LaunchDetailState.ERROR,
            launch = null,
        ),
    )
}
