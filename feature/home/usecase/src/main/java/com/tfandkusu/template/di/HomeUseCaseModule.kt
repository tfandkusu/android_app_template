package com.tfandkusu.template.di

import com.tfandkusu.template.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.template.usecase.home.HomeFavoriteUseCaseImpl
import com.tfandkusu.template.usecase.home.HomeLoadUseCase
import com.tfandkusu.template.usecase.home.HomeLoadUseCaseImpl
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindHomeLoadUseCase(
        useCase: HomeLoadUseCaseImpl
    ): HomeLoadUseCase

    @Binds
    @Singleton
    abstract fun bindOnCreateUseCase(
        useCase: HomeOnCreateUseCaseImpl
    ): HomeOnCreateUseCase

    @Binds
    @Singleton
    abstract fun bindFavoriteUseCase(
        useCase: HomeFavoriteUseCaseImpl
    ): HomeFavoriteUseCase
}
