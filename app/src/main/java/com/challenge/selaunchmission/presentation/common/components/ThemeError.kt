package com.challenge.selaunchmission.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme

@Composable
fun ThemeError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launch_error),
            contentDescription = null,
        )
        Text(
            text = "Oops, an error happened. Please check the Internet or try again later.",
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onRetry,
        ) {
            Text(
                text = "Try again"
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ThemeErrorPreview() {
    PreviewTheme {
        ThemeError(onRetry = {})
    }
}
