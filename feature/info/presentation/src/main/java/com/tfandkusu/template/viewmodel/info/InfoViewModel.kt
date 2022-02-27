package com.tfandkusu.template.viewmodel.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.viewmodel.UnidirectionalViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class InfoEvent {
    object OnClickAbout : InfoEvent()
}

sealed class InfoEffect {
    data class ShowAbout(val numberOfStarts: Int) : InfoEffect()
}

object InfoState

interface InfoViewModel : UnidirectionalViewModel<InfoEvent, InfoEffect, InfoState>

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    private val onClickAboutUseCase: InfoOnClickAboutUseCase
) : InfoViewModel, ViewModel() {

    override fun createDefaultState() = InfoState

    private val _state = MutableLiveData(createDefaultState())

    override val state: LiveData<InfoState>
        get() = _state

    private val effectChannel = createEffectChannel()

    override val effect = effectChannel.receiveAsFlow()

    override fun event(event: InfoEvent) {
        viewModelScope.launch {
            when (event) {
                InfoEvent.OnClickAbout -> {
                    val result = onClickAboutUseCase.execute()
                    effectChannel.send(InfoEffect.ShowAbout(result.numberOfStarts))
                }
            }
        }
    }
}
