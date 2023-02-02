package com.raghav.spacedawn.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raghav.spacedawn.models.launchlibrary.LaunchServiceProvider
import com.raghav.spacedawn.models.launchlibrary.Rocket
import com.raghav.spacedawn.models.launchlibrary.Status

object TypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun toLaunchServiceProvider(value: String): LaunchServiceProvider {
        return gson.fromJson(
            value,
            object : TypeToken<LaunchServiceProvider>() {
            }.type
        )
    }

    @TypeConverter
    fun fromLaunchServiceProvider(value: LaunchServiceProvider?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toRocket(value: String): Rocket {
        return gson.fromJson(
            value,
            object : TypeToken<Rocket>() {
            }.type
        )
    }

    @TypeConverter
    fun fromRocket(value: Rocket?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStatus(value: String): Status {
        return gson.fromJson(
            value,
            object : TypeToken<Status>() {
            }.type
        )
    }

    @TypeConverter
    fun fromStatus(value: Status?): String? {
        return gson.toJson(value)
    }

}
















