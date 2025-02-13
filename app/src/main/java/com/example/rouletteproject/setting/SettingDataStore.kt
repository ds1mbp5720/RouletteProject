package com.example.rouletteproject.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.rouletteproject.MyApplication

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object SettingDataStore {
    private val settingDataStore by lazy { MyApplication.instance.settingDataStore }

    //todo 설정값 5종류
}