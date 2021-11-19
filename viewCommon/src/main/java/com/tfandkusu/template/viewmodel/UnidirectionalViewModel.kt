package com.tfandkusu.template.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface UnidirectionalViewModel<EVENT, EFFECT, STATE> {
    val state: LiveData<STATE>
    val effect: Flow<EFFECT>

    fun event(event: EVENT)

    fun createEffectChannel(): Channel<EFFECT> {
        return Channel(Channel.UNLIMITED)
    }
}
