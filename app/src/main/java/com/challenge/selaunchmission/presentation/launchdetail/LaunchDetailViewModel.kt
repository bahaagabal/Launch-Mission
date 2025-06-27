package com.challenge.selaunchmission.presentation.launchdetail

import androidx.lifecycle.ViewModel
import com.challenge.selaunchmission.domain.GetLaunchInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val getLaunchInfoUseCase: GetLaunchInfoUseCase
) : ViewModel() {

    var state: MutableStateFlow<LaunchDetailViewState> = MutableStateFlow(LaunchDetailViewState())

    var sideEffect: MutableSharedFlow<LaunchDetailSideEffect> = MutableSharedFlow()

    suspend fun onEvent(event: LaunchDetailEvent) {
        when (event) {
            is LaunchDetailEvent.OnScreenLoaded -> loadLaunchInfo(event.launchId)
            is LaunchDetailEvent.Reload -> loadLaunchInfo(event.launchId)
            is LaunchDetailEvent.Close -> {
                sideEffect.emit(LaunchDetailSideEffect.Close)
            }
        }
    }

    private suspend fun loadLaunchInfo(launchId: String) {
        getLaunchInfoUseCase.execute(launchId)?.let { launch ->
            state.update {
                it.copy(
                    viewState = LaunchDetailState.SHOW_DETAIL,
                    launch = launch,
                )
            }
        } ?: run {
            state.update {
                it.copy(
                    viewState = LaunchDetailState.ERROR,
                    launch = null,
                )
            }
        }
    }
}
