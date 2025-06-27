package com.challenge.selaunchmission.presentation.launchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.challenge.selaunchmission.presentation.common.components.ThemeError
import com.challenge.selaunchmission.presentation.common.components.ThemeLoader
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme
import com.challenge.selaunchmission.presentation.launchlist.components.LaunchList
import com.challenge.selaunchmission.presentation.launchlist.components.LaunchListEmpty
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchListScreen(
    viewState: LaunchListViewState,
    onEvent: suspend (LaunchListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .testTag("launchListScreen"),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Launches") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddings ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = paddings.calculateTopPadding() + 16.dp, // بدل Spacer خارجي
                    bottom = paddings.calculateBottomPadding()
                )
        ) {
            when (viewState.viewState) {
                LaunchListState.LOADING -> {
                    ThemeLoader(modifier = Modifier.fillMaxSize())
                }

                LaunchListState.ERROR -> {
                    ThemeError(
                        onRetry = {
                            scope.launch { onEvent(LaunchListEvent.Reload) }
                        }
                    )
                }

                else -> {
                    if (viewState.launches.isEmpty()) {
                        LaunchListEmpty(
                            onRetry = {
                                scope.launch { onEvent(LaunchListEvent.Reload) }
                            }
                        )
                    } else {
                       LaunchList(
                            viewState = viewState,
                            onLoadMore = {
                                scope.launch { onEvent(LaunchListEvent.LoadMore) }
                            },
                            onSelectLaunch = { launch ->
                                scope.launch { onEvent(LaunchListEvent.SelectLaunch(launch)) }
                            }
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun LaunchListScreenPreview(
    @PreviewParameter(LaunchListPreviewParameterProvider::class) viewState: LaunchListViewState
) {
    PreviewTheme {
        LaunchListScreen(
            viewState = viewState,
            onEvent = {},
        )
    }
}
