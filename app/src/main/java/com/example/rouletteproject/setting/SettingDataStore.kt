package com.example.rouletteproject.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rouletteproject.RouletteApp
import com.example.rouletteproject.navigation.MainDestination
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object SettingDataStore {
    private val settingDataStore by lazy { RouletteApp.instance.settingDataStore }

    fun setFirstScreen(screen: String) = runBlocking {
        val firstScreenKey = stringPreferencesKey("FirstScreen")
        settingDataStore.edit {
            it[firstScreenKey] = screen
        }
    }
    fun getFirstScreen(): String = runBlocking {
        val firstScreenKey = stringPreferencesKey("FirstScreen")
        return@runBlocking settingDataStore.data.map { it[firstScreenKey] }.first() ?: MainDestination.ROULETTE
    }

    fun setDragRotate(isSet: Boolean) = runBlocking {
        val firstSettingKey = booleanPreferencesKey("DragRotate")
        settingDataStore.edit {
            it[firstSettingKey] = isSet
        }
    }
    fun getDragRotate() : Boolean = runBlocking {
        val firstSettingKey = booleanPreferencesKey("DragRotate")
        return@runBlocking settingDataStore.data.map { it[firstSettingKey] }.first() ?: true
    }

    fun setSelectCardReverse(isSet: Boolean) = runBlocking {
        val firstSettingKey = booleanPreferencesKey("SelectCardReverse")
        settingDataStore.edit {
            it[firstSettingKey] = isSet
        }
    }
    fun getSelectCardReverse() : Boolean = runBlocking {
        val firstSettingKey = booleanPreferencesKey("SelectCardReverse")
        return@runBlocking settingDataStore.data.map { it[firstSettingKey] }.first() ?: true
    }

    fun setLadderMoveTime(isTime: Int) = runBlocking {
        val firstSettingKey = intPreferencesKey("LadderMoveTime")
        settingDataStore.edit {
            it[firstSettingKey] = isTime
        }
    }
    fun getLadderMoveTime() : Int = runBlocking {
        val firstSettingKey = intPreferencesKey("LadderMoveTime")
        return@runBlocking settingDataStore.data.map { it[firstSettingKey] }.first() ?: 3
    }

    fun setLadderRowCount(isCount: Int) = runBlocking {
        val firstSettingKey = intPreferencesKey("LadderRowCount")
        settingDataStore.edit {
            it[firstSettingKey] = isCount
        }
    }
    fun getLadderRowCount() : Int = runBlocking {
        val firstSettingKey = intPreferencesKey("LadderRowCount")
        return@runBlocking settingDataStore.data.map { it[firstSettingKey] }.first() ?: 6
    }
}