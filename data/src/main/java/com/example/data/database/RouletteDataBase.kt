package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.converter.ListConverters
import com.example.data.dao.RouletteDao
import com.example.data.entity.RouletteEntity

@Database(entities = [RouletteEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverters::class) // list 사용을 위한 converter
abstract class RouletteDataBase : RoomDatabase() {
    abstract fun getRouletteDao(): RouletteDao

    companion object {
        @Volatile
        private var INSTANCE: RouletteDataBase? = null

        private fun buildDataBase(context: Context): RouletteDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                RouletteDataBase::class.java,
                "roulette"
            ).fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): RouletteDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
    }
}