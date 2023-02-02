package com.raghav.spacedawn.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem

object TypeConverter {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun toArticleResponseItem(value: String): ArticlesResponseItem {
        return gson.fromJson(
            value,
            object : TypeToken<ArticlesResponseItem>() {
            }.type
        )
    }

    @TypeConverter
    @JvmStatic
    fun fromArticleResponseItem(value: ArticlesResponseItem?): String? {
        return gson.toJson(value)
    }

//
//    @TypeConverter
//    fun eventsToJson(value: List<Any>?) = gson.toJson(value)
//
//    @TypeConverter
//    fun jsonToEvents(value: String) = gson.fromJson(value, Array<Any>::class.java).toList()
//
//    @TypeConverter
//    fun launchesToJson(value: List<Launche>) = gson.toJson(value)
//
//    @TypeConverter
//    fun jsonToLaunches(value: String) = gson.fromJson(value, Array<Launche>::class.java).toList()

}

















