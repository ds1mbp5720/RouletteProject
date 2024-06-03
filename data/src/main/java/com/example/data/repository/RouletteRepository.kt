package com.example.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.dao.RouletteDao
import com.example.data.database.RouletteDataBase
import com.example.data.entity.RouletteEntity
import kotlinx.coroutines.flow.Flow

class RouletteRepository(application: Application) {
    private val rouletteDataBase = RouletteDataBase.getInstance(application)
    private val rouletteDao: RouletteDao = rouletteDataBase.getRouletteDao()
    val allRoulette: Flow<List<RouletteEntity>> = rouletteDao.getRouletteList()

    fun insert(roulette: RouletteEntity) {
        try {
            rouletteDao.insertRoulette(roulette = roulette)
        } catch (e: Exception) {
            Log.e("","mainViewModel(insert) error $e")
        }
    }

    fun update(roulette: RouletteEntity) {
        try {
            rouletteDao.update(roulette = roulette)
        } catch (e: Exception) {
            Log.e("","mainViewModel(update) error $e")
        }
    }

    fun delete(id: Long) {
        try {
            rouletteDao.delete(id)
        } catch (e: Exception) {
            Log.e("","mainViewModel(delete) error $e")
        }
    }
}