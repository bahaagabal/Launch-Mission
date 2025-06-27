package com.challenge.selaunchmission.presentation.launchlist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LaunchListPageRetry(
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { onLoadMore() }
    ) {
        Text(
            text = "Failed to load more, tap here to retry"
        )
    }
}
