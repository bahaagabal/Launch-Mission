package com.challenge.selaunchmission.presentation.launchdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.domain.models.Rocket
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme

@Composable
fun RocketInfo(rocket: Rocket) {
    Column {
        TitleText(
            text = "Rocket",
        )
        DescriptionText(
            text = "NAME: ${rocket.name}",
            modifier = Modifier.testTag("rocketName"),
        )
        DescriptionText(
            text = "TYPE: ${rocket.type}",
            modifier = Modifier.testTag("rocketType"),
        )
        DescriptionText(
            text = "ID: ${rocket.id}",
            modifier = Modifier.testTag("rocketId"),
        )
    }
}

@PreviewLightDark
@Composable
fun RocketInfoPreview() {
    PreviewTheme {
        RocketInfo(
            rocket = Rocket(
                id = "falcon9",
                name = "Falcon 9",
                type = "FT",
            )
        )
    }
}
