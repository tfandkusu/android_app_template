package com.tfandkusu.template.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfandkusu.template.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.template.usecase.home.HomeLoadUseCase
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.template.viewmodel.error.ApiErrorState
import com.tfandkusu.template.viewmodel.error.mapToApiErrorState
import com.tfandkusu.template.viewmodel.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val loadUseCase: HomeLoadUseCase,
    private val onCreateUseCase: HomeOnCreateUseCase,
    private val favoriteUseCase: HomeFavoriteUseCase
) : HomeViewModel, ViewModel() {

    override fun createDefaultState() = HomeState()

    private val _state = MutableLiveData(createDefaultState())

    override val state: LiveData<HomeState> = _state

    private val effectChannel = createEffectChannel()

    override val effect: Flow<HomeEffect> = effectChannel.receiveAsFlow()

    override fun event(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                HomeEvent.Load -> {
                    _state.update {
                        copy(progress = true, error = ApiErrorState())
                    }
                    try {
                        loadUseCase.execute()
                        _state.update {
                            copy(progress = false)
                        }
                    } catch (e: Throwable) {
                        _state.update {
                            copy(progress = false, error = mapToApiErrorState(e))
                        }
                    }
                }
                HomeEvent.OnCreate -> {
                    onCreateUseCase.execute().collect { repos ->
                        _state.update {
                            copy(
                                items = repos.map {
                                    HomeStateItem(
                                        it
                                    )
                                }
                            )
                        }
                    }
                }
                is HomeEvent.Favorite -> {
                    favoriteUseCase.execute(event.id, event.on)
                }
            }
        }
    }
}
