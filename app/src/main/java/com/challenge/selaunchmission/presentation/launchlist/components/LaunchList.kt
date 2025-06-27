package com.challenge.selaunchmission.presentation.launchlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.challenge.selaunchmission.R
import com.challenge.selaunchmission.domain.models.LaunchSnippet
import com.challenge.selaunchmission.presentation.extensions.lazyListItemPosition
import com.challenge.selaunchmission.presentation.launchlist.LaunchListState
import com.challenge.selaunchmission.presentation.launchlist.LaunchListViewModel
import com.challenge.selaunchmission.presentation.launchlist.LaunchListViewState

@Composable
fun LaunchList(
    viewState: LaunchListViewState,
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit,
    onSelectLaunch: (LaunchSnippet) -> Unit,
) {
    val lazyColumnListState = LazyListState()

    val shouldLoadMore: Boolean =
        viewState.canLoadMore &&
                (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: 0) >= (lazyColumnListState.layoutInfo.totalItemsCount - LaunchListViewModel.LOAD_MORE_WHEN_SEEING_LAST_X_ITEM)

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = modifier.testTag("launchList"),
        contentPadding = PaddingValues(vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        // state = lazyColumnListState,
    ) {
        items(
            items = viewState.launches,
            key = { item -> item.id }
        ) { launch ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectLaunch(launch) }
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    val (logo, rocketName, missionName) = createRefs()
                    createVerticalChain(rocketName, missionName, chainStyle = ChainStyle.Packed)

                    AsyncImage(
                        modifier = Modifier
                            .constrainAs(logo) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                            .size(64.dp),
                        model = launch.missionPatch,
                        contentDescription = launch.missionName,
                        placeholder = painterResource(id = R.drawable.ic_mission_placeholder),
                        error = painterResource(id = R.drawable.ic_mission_not_found),
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(rocketName) {
                                top.linkTo(parent.top)
                                start.linkTo(logo.end, 8.dp)
                                end.linkTo(parent.end, 8.dp)
                                width = Dimension.fillToConstraints
                            }
                            .testTag("rocketName"),
                        text = launch.rocketName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(missionName) {
                                top.linkTo(rocketName.bottom)
                                start.linkTo(rocketName.start)
                                end.linkTo(rocketName.end)
                                width = Dimension.fillToConstraints
                            }
                            .testTag("missionName"),
                        text = launch.missionName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        if (viewState.viewState == LaunchListState.PAGE_LOADING) {
            item(key = "pageLoading") {
                LaunchListPageLoading()
            }
        } else if (viewState.viewState == LaunchListState.PAGE_ERROR) {
            item(key = "pageError") {
                LaunchListPageRetry(
                    onLoadMore = onLoadMore,
                )
                LaunchedEffect(Unit) {
                    lazyColumnListState.animateScrollToItem(viewState.launches.size)
                }
            }
        }
    }
}
