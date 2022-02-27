package com.tfandkusu.template.usecase.info

import com.tfandkusu.template.data.repository.StartupTimesRepository
import javax.inject.Inject

data class InfoOnClickAboutUseCaseResult(val startupTimes: Int)

interface InfoOnClickAboutUseCase {
    fun execute(): InfoOnClickAboutUseCaseResult
}

class InfoOnClickAboutUseCaseImpl @Inject constructor(
    private val repository: StartupTimesRepository
) : InfoOnClickAboutUseCase {
    override fun execute(): InfoOnClickAboutUseCaseResult {
        return InfoOnClickAboutUseCaseResult(repository.get())
    }
}
