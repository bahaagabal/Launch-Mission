package com.challenge.selaunchmission.presentation.common.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme

@Composable
fun ThemeLoader(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations =  LottieConstants.IterateForever,
    )
}

@PreviewLightDark
@Composable
fun ThemeLoaderPreview() {
    PreviewTheme {
        ThemeLoader()
    }
}
