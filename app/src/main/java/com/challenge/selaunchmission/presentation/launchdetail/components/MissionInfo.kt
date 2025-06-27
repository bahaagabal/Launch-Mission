package com.challenge.selaunchmission.presentation.launchdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.domain.models.Mission
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme

@Composable
fun MissionInfo(mission: Mission) {
    Column {
        TitleText(
            text = "Mission:",
        )
        DescriptionText(
            text = "NAME: ${mission.name}",
            modifier = Modifier.testTag("missionName"),
        )
    }
}

@PreviewLightDark
@Composable
fun MissionInfoPreview() {
    PreviewTheme {
        MissionInfo(
            mission = Mission(
                name = "CRS-21",
                missionPatch = "https://imgur.com/E7fjUBD.png"
            )
        )
    }
}
