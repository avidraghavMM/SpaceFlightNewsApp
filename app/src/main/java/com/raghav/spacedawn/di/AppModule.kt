package com.raghav.spacedawn.di

import android.content.Context
import androidx.room.Room
import com.raghav.spacedawn.db.ReminderDao
import com.raghav.spacedawn.db.ReminderDatabase
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.repository.IAppRepository
import com.raghav.spacedawn.utils.AppApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideReminderDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ReminderDatabase::class.java,
        "reminder_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideReminderDao(database: ReminderDatabase) = database.getRemindersDao()

    @Singleton
    @Provides
    fun provideRepository(dao: ReminderDao): IAppRepository = AppRepository(dao)

    @Provides
    @Singleton
    fun provideAppApplication(@ApplicationContext app: Context): AppApplication {
        return app as AppApplication
    }
}
