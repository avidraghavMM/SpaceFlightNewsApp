package com.raghav.spacedawn.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raghav.spacedawn.db.launchlibrary.LaunchLibraryDao
import com.raghav.spacedawn.db.launchlibrary.LaunchLibraryKeysDao
import com.raghav.spacedawn.db.spaceflightapi.ArticlesApiKeysDao
import com.raghav.spacedawn.db.spaceflightapi.ArticlesDao
import com.raghav.spacedawn.models.ArticlesApiKeys
import com.raghav.spacedawn.models.LaunchLibraryKeys
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem

@Database(
    entities = [ReminderModelClass::class, ArticlesResponseItem::class, LaunchLibraryResponseItem::class,
        LaunchLibraryKeys::class, ArticlesApiKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRemindersDao(): ReminderDao
    abstract fun getSpaceFlightDao(): ArticlesDao
    abstract fun getLaunchLibraryDao(): LaunchLibraryDao
    abstract fun getLaunchLibraryKeysDao(): LaunchLibraryKeysDao
    abstract fun getArticlesKeysDao(): ArticlesApiKeysDao

}
