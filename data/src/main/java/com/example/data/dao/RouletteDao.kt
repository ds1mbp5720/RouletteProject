package com.example.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.RouletteEntity

@Dao
interface RouletteDao {
    @Query("SELECT * FROM Roulette")
    fun getRouletteList(): LiveData<List<RouletteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoulette(roulette: RouletteEntity)

    @Update
    fun update(roulette: RouletteEntity)

    @Query("DELETE FROM Roulette WHERE id = :id")
    fun delete(id: Int)
}