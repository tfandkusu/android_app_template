package com.tfandkusu.template.di

import com.tfandkusu.template.data.repository.DummyRepository
import com.tfandkusu.template.data.repository.DummyRepositoryImpl
import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.data.repository.GithubRepoRepositoryImpl
import com.tfandkusu.template.data.repository.NumberOfStartsRepository
import com.tfandkusu.template.data.repository.NumberOfStartsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGithubRepoRepository(
        repository: GithubRepoRepositoryImpl
    ): GithubRepoRepository

    @Binds
    @Singleton
    abstract fun bindNumberOfStartsRepository(
        repository: NumberOfStartsRepositoryImpl
    ): NumberOfStartsRepository

    @Binds
    @Singleton
    internal abstract fun bindDummyRepository(
        repository: DummyRepositoryImpl
    ): DummyRepository
}
