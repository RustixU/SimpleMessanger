package rut.miit.simplemessanger.converters

import androidx.room.TypeConverter

object TypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(",")
    }
}