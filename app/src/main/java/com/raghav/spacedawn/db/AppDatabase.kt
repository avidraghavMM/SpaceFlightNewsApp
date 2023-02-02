package com.raghav.spacedawn.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem

@Database(
    entities = [ReminderModelClass::class, ArticlesResponseItem::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRemindersDao(): ReminderDao
    abstract fun getSpaceFlightDao(): SpaceFlightDao

}
