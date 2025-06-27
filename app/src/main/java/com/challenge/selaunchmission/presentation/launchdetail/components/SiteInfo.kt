package com.challenge.selaunchmission.presentation.launchdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme

@Composable
fun SiteInfo(site: String) {
    Column {
        TitleText(
            text = "Site:",
        )
        DescriptionText(
            text = site,
            modifier = Modifier.testTag("siteName"),
        )
    }
}

@PreviewLightDark
@Composable
fun SiteInfoPreview() {
    PreviewTheme {
        SiteInfo(
            site = "KSC LC 39A",
        )
    }
}
