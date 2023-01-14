package com.tfandkusu.template.di

import com.tfandkusu.template.viewmodel.home.HomeActionCreator
import com.tfandkusu.template.viewmodel.home.HomeActionCreatorImpl
import com.tfandkusu.template.viewmodel.home.HomeReducer
import com.tfandkusu.template.viewmodel.home.HomeReducerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomePresentationModule {

    @Binds
    abstract fun bindHomeActionCreator(actionCreator: HomeActionCreatorImpl): HomeActionCreator

    @Binds
    @Singleton
    abstract fun bindHomeReducer(homeReducer: HomeReducerImpl): HomeReducer
}
