package com.challenge.selaunchmission.presentation.common.compose

import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.challenge.selaunchmission.ui.theme.LaunchMissionTheme

@Composable
fun PreviewTheme(
    content: @Composable () -> Unit
) {
    LaunchMissionTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            content()
        }
    }
}
