package com.way.suitmediamobile.di

import com.way.suitmediamobile.presentation.ui.adapter.EventAdapter
import com.way.suitmediamobile.presentation.ui.adapter.GuestAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Provides
    @Singleton
    fun provideAdapter(): GuestAdapter = GuestAdapter()

    @Provides
    @Singleton
    fun provideEventAdapter(): EventAdapter = EventAdapter()
}