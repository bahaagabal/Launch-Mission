package com.challenge.selaunchmission.presentation.launchdetail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme

@Composable
fun DescriptionText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
fun DescriptionTextPreview() {
    PreviewTheme {
        DescriptionText(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
        )
    }
}
