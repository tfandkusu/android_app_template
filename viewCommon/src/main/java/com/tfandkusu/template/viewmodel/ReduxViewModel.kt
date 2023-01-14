package com.tfandkusu.template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class ReduxViewModel<EVENT, ACTION, STATE, EFFECT>(
    private val actionCreator: ActionCreator<EVENT, ACTION>,
    private val reducer: Reducer<ACTION, STATE, EFFECT>
) :
    UnidirectionalViewModel<EVENT, EFFECT, STATE>, ViewModel() {

    private val _state = MutableLiveData(this.createDefaultState())

    override val state: LiveData<STATE>
        get() = _state

    private val channel = Channel<EFFECT>(Channel.UNLIMITED)

    override val effect: Flow<EFFECT>
        get() = channel.receiveAsFlow()

    override fun event(event: EVENT) {
        viewModelScope.launch {
            actionCreator.event(
                event,
                object : Dispatcher<ACTION> {
                    override suspend fun dispatch(action: ACTION) {
                        state.value?.let { currentState ->
                            val stateEffect = reducer.reduce(currentState, action)
                            // Set next state
                            _state.value = stateEffect.state
                            // Set effect
                            stateEffect.effect?.let { effect ->
                                channel.send(effect)
                            }
                        }
                    }
                }
            )
        }
    }
}
