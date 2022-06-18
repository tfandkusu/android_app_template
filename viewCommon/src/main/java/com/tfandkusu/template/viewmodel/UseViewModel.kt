package com.tfandkusu.template.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow

data class StateEffectDispatch<STATE, EFFECT, EVENT>(
    val state: STATE,
    val effectFlow: Flow<EFFECT>,
    val dispatch: (EVENT) -> Unit,
)

@Composable
inline fun <reified STATE, EFFECT, EVENT> use(
    viewModel: UnidirectionalViewModel<EVENT, EFFECT, STATE>,
): StateEffectDispatch<STATE, EFFECT, EVENT> {
    val state = viewModel.state.observeAsState(viewModel.createDefaultState()).value
    val dispatch = remember(viewModel) {
        { event: EVENT ->
            viewModel.event(event)
        }
    }
    return StateEffectDispatch(
        state = state,
        effectFlow = viewModel.effect,
        dispatch = dispatch
    )
}
