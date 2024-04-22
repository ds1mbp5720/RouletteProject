package com.example.rouletteproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.data.entity.RouletteEntity
import com.example.data.repository.RouletteRepository
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
): AndroidViewModel(application) {
    private val repository = RouletteRepository(application)
    private val rouletteList = repository.getAllRouletteList()

    fun getAllRoulette(): LiveData<List<RouletteEntity>> {
        return this.rouletteList
    }
    fun insert(roulette: RouletteEntity) {
        viewModelScope.launch {
            repository.insert(roulette)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}