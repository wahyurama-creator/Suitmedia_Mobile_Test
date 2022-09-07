package com.way.suitmediamobile.di

import android.app.Application
import com.way.suitmediamobile.data.Repository
import com.way.suitmediamobile.presentation.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideViewModelFactory(
        repository: Repository,
        app: Application
    ): ViewModelFactory = ViewModelFactory(
        repository, app
    )
}