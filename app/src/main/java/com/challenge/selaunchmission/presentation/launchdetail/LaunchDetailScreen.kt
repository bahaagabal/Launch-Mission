package com.challenge.selaunchmission.presentation.launchdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.presentation.common.components.ThemeError
import com.challenge.selaunchmission.presentation.common.compose.PreviewTheme
import com.challenge.selaunchmission.presentation.launchdetail.components.MissionInfo
import com.challenge.selaunchmission.presentation.launchdetail.components.RocketInfo
import com.challenge.selaunchmission.presentation.launchdetail.components.SiteInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailScreen(
    launchId: String,
    viewState: LaunchDetailViewState,
    onEvent: suspend (LaunchDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .testTag("launchDetailScreen"),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag("goBack"),
                        onClick = {
                            scope.launch {
                                onEvent(LaunchDetailEvent.Close)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "go back"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Launch Detail (id: $launchId)",
                        modifier = Modifier.testTag("launchDetailTitle")
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        when (viewState.viewState) {
            LaunchDetailState.EMPTY ->
                Unit

            LaunchDetailState.ERROR -> {
                ThemeError(
                    onRetry = {
                        scope.launch {
                            onEvent(LaunchDetailEvent.Reload(launchId))
                        }
                    },
                    modifier = modifier
                        .padding(16.dp)
                        .padding(paddingValues)
                )
            }

            LaunchDetailState.SHOW_DETAIL -> {
                viewState.launch?.let { launch ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AsyncImage(
                            model = launch.mission.missionPatch,
                            contentDescription = launch.mission.name,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = painterResource(id = R.drawable.ic_mission_placeholder),
                            error = painterResource(id = R.drawable.ic_mission_not_found),
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(24.dp),

                            ) {
                            RocketInfo(rocket = launch.rocket)
                            MissionInfo(mission = launch.mission)
                            SiteInfo(site = launch.site)
                        }
                    }
                } ?: Text("No launch information")
            }

            LaunchDetailState.LOADING -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        LaunchedEffect(Unit) {
            onEvent(LaunchDetailEvent.OnScreenLoaded(launchId))
        }
    }
}

@PreviewLightDark
@Composable
fun LaunchDetailScreenPreview(
    @PreviewParameter(LaunchDetailPreviewParameterProvider::class) viewState: LaunchDetailViewState
) {
    PreviewTheme {
        LaunchDetailScreen(
            launchId = "110",
            viewState = viewState,
            onEvent = {},
        )
    }
}
