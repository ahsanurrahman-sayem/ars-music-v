package com.musicplayer.core.data.database

import androidx.room.TypeConverter

/**
 * Room type converters for complex types.
 */
class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String> {
        return if (value.isNullOrEmpty()) {
            emptyList()
        } else {
            value.split(",")
        }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}
