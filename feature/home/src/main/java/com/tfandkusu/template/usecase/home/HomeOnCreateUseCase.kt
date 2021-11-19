package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface HomeOnCreateUseCase {
    fun execute(): Flow<List<GithubRepo>>
}

class HomeOnCreateUseCaseImpl(
    private val repository: GithubRepoRepository
) : HomeOnCreateUseCase {
    override fun execute() = repository.listAsFlow()
}
