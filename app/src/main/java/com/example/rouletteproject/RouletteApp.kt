package com.example.rouletteproject

import android.app.Application

class RouletteApp : Application() {
    init {
        instance = this
    }
    companion object {
        lateinit var instance: RouletteApp
    }
    override fun onCreate() {
        super.onCreate()
    }
}