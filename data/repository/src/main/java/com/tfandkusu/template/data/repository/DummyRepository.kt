package com.tfandkusu.template.data.repository

import javax.inject.Inject

interface DummyRepository {
    fun getFlag(): Boolean
}

internal class DummyRepositoryImpl @Inject constructor() : DummyRepository {
    override fun getFlag(): Boolean {
        return true
    }
}
