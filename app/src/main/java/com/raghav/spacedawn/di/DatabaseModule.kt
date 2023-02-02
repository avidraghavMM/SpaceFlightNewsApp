package com.raghav.spacedawn.di

import android.content.Context
import androidx.room.Room
import com.raghav.spacedawn.db.AppDatabase
import com.raghav.spacedawn.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideReminderDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideReminderDao(database: AppDatabase) = database.getRemindersDao()

    @Singleton
    @Provides
    fun provideSpaceFlightDao(database: AppDatabase) = database.getSpaceFlightDao()

    @Singleton
    @Provides
    fun provideLaunchLibraryDao(database: AppDatabase) = database.getLaunchLibraryDao()
}
