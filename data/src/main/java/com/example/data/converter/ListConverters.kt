package com.example.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * room 에서 list 형식 사용을 위한 gson 사용 type converter
 * DataBase 에서 선언
  */
class ListConverters {
    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value,Array<String>::class.java)?.toList()
    }
}