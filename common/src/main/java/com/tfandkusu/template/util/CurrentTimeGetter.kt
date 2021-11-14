package com.tfandkusu.template.util

interface CurrentTimeGetter {
    fun get(): Long
}

class CurrentTimeGetterImpl : CurrentTimeGetter {
    override fun get() = System.currentTimeMillis()
}
