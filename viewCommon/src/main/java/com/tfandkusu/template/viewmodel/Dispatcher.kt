package com.tfandkusu.template.viewmodel

interface Dispatcher<ACTION> {
    /**
     * Called in ActionCreator to dispatch action.
     */
    suspend fun dispatch(action: ACTION)
}
