package com.tfandkusu.template.viewmodel

interface Dispatcher<ACTION> {
    suspend fun dispatch(action: ACTION)
}
