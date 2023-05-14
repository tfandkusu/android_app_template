package com.tfandkusu.template.di

import com.tfandkusu.template.viewmodel.info.InfoActionCreator
import com.tfandkusu.template.viewmodel.info.InfoActionCreatorImpl
import com.tfandkusu.template.viewmodel.info.InfoReducer
import com.tfandkusu.template.viewmodel.info.InfoReducerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InfoPresentationModule {

    @Binds
    abstract fun bindInfoActionCreator(actionCreator: InfoActionCreatorImpl): InfoActionCreator

    @Binds
    @Singleton
    abstract fun bindInfoReducer(homeReducer: InfoReducerImpl): InfoReducer
}
