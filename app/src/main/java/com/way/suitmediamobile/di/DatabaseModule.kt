package com.way.suitmediamobile.di

import android.content.Context
import androidx.room.Room
import com.way.suitmediamobile.data.local.room.PersonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PersonDatabase::class.java,
        "person_database.db"
    ).build()

    @Provides
    @Singleton
    fun provideDao(database: PersonDatabase) = database.personDao()
}