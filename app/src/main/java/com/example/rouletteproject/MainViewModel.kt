package com.example.rouletteproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.entity.RouletteEntity
import com.example.data.repository.RouletteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
): AndroidViewModel(application) {
    private val repository = RouletteRepository(application)
    val rouletteList: LiveData<List<RouletteEntity>> = repository.allRoulette.asLiveData()

    fun getAllList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllList()
        }
    }

    fun insert(roulette: RouletteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(roulette)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }

    fun update(roulette: RouletteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(roulette)
        }
    }
}