package com.tfandkusu.template.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

/**
 * Reference: https://github.com/DroidKaigi/conference-app-2021/blob/main/uicomponent-compose/core/src/main/java/io/github/droidkaigi/feeder/core/UnidirectionalViewModel.kt
 */
interface UnidirectionalViewModel<EVENT, EFFECT, STATE> {

    fun createDefaultState(): STATE

    val state: LiveData<STATE>
    val effect: Flow<EFFECT>

    fun event(event: EVENT)

    fun createEffectChannel(): Channel<EFFECT> {
        return Channel(Channel.UNLIMITED)
    }
}

@Composable
inline fun <reified STATE, EFFECT, EVENT> useState(
    viewModel: UnidirectionalViewModel<EVENT, EFFECT, STATE>
): STATE {
    return viewModel.state.observeAsState().value ?: viewModel.createDefaultState()
}
