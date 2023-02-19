package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.DummyRepository
import com.tfandkusu.template.data.repository.GithubRepoRepository
import javax.inject.Inject

interface HomeLoadUseCase {
    suspend fun execute()
}

class HomeLoadUseCaseImpl @Inject constructor(
    private val repository: GithubRepoRepository,
    private val dummyRepository: DummyRepository
) : HomeLoadUseCase {
    override suspend fun execute() {
        if (repository.isCacheExpired() && dummyRepository.getFlag()) {
            repository.fetch()
        }
    }
}
