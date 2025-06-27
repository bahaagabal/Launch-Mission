package com.challenge.selaunchmission.presentation.launchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.selaunchmission.domain.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase,
) : ViewModel() {

    var state: MutableStateFlow<LaunchListViewState> = MutableStateFlow(LaunchListViewState())

    var sideEffect: MutableSharedFlow<LaunchListSideEffect> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            loadInitialLaunches()
        }
    }

    suspend fun onEvent(event: LaunchListEvent) {
        when (event) {
            is LaunchListEvent.Reload -> {
                loadInitialLaunches()
            }

            is LaunchListEvent.SelectLaunch -> {
                Timber.d("User selects the mission ${event.launch.missionName}")
                sideEffect.emit(LaunchListSideEffect.OpenLaunchDetails(event.launch.id))
            }

            is LaunchListEvent.LoadMore -> {
                if (state.value.canLoadMore && state.value.viewState != LaunchListState.PAGE_LOADING) {
                    loadMoreLaunches()
                }
            }
        }
    }

    private suspend fun loadInitialLaunches() {
        state.update {
            it.copy(viewState = LaunchListState.LOADING)
        }

        getLaunchesUseCase.execute(PAGE_SIZE, null)?.let { launchResult ->
            state.update {
                it.copy(
                    launches = launchResult.launches,
                    canLoadMore = launchResult.canLoadMore,
                    viewState = LaunchListState.SHOW_LIST,
                )
            }
        } ?: run {
            state.update {
                it.copy(
                    launches = emptyList(),
                    canLoadMore = false,
                    viewState = LaunchListState.ERROR,
                )
            }
        }
    }

    private suspend fun loadMoreLaunches() {
        state.update {
            it.copy(viewState = LaunchListState.PAGE_LOADING)
        }
        getLaunchesUseCase.execute(PAGE_SIZE, null)?.let { launchResult ->
            state.update {
                it.copy(
                    launches = state.value.launches + launchResult.launches,
                    canLoadMore = launchResult.canLoadMore,
                    viewState = LaunchListState.SHOW_LIST,
                )
            }
        } ?: run {
            state.update {
                it.copy(
                    viewState = LaunchListState.PAGE_ERROR,
                )
            }
        }
    }

    companion object {
        const val PAGE_SIZE: Int = 15

        /* The position from the bottom to start loading more launches when the user sees it
         * e.g.
         * 1 means load more only when the user sees the last item
         * 2 means load more only when the user sees the item before the last item
         */
        const val LOAD_MORE_WHEN_SEEING_LAST_X_ITEM: Int = 3
    }
}
