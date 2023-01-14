package com.tfandkusu.template.viewmodel

/**
 * Next state and effect
 *
 * @param state next state
 * @param effect one shot operation such as navigation
 */
data class StateEffect<STATE, EFFECT>(val state: STATE, val effect: EFFECT? = null)

interface Reducer<ACTION, STATE, EFFECT> {
    /**
     * Receive current state and action.
     * And return next state and effect.
     *
     * @param state Current state
     * @param action Action
     *
     * @return Next state and effect
     */
    fun reduce(state: STATE, action: ACTION): StateEffect<STATE, EFFECT>
}
