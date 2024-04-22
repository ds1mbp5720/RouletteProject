package com.example.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.data.dao.RouletteDao
import com.example.data.database.RouletteDataBase
import com.example.data.entity.RouletteEntity

class RouletteRepository(application: Application) {
    private val rouletteDataBase = RouletteDataBase.getInstance(application)
    private val rouletteDao: RouletteDao = rouletteDataBase.getRouletteDao()
    private val rouletteLists: LiveData<List<RouletteEntity>> = rouletteDao.getRouletteList()

    fun getAllRouletteList(): LiveData<List<RouletteEntity>> {
        return rouletteLists
    }

    fun insert(roulette: RouletteEntity) {
        try {
            rouletteDao.insertRoulette(roulette = roulette)
        } catch (e: Exception) {}
    }

    fun delete(id: Int) {
        try {
            rouletteDao.delete(id)
        } catch (e: Exception) {}
    }
}