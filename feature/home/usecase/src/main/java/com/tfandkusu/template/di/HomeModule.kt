package com.tfandkusu.template.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    @Singleton
    abstract fun bindHomeLoadUseCase(
        useCase: com.tfandkusu.template.usecase.home.HomeLoadUseCaseImpl
    ): com.tfandkusu.template.usecase.home.HomeLoadUseCase

    @Binds
    @Singleton
    abstract fun bindOnCreateUseCase(
        useCase: com.tfandkusu.template.usecase.home.HomeOnCreateUseCaseImpl
    ): com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
}
