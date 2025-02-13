package com.example.rouletteproject

import android.app.Application

class MyApplication: Application() {
    init {
        instance = this
    }
    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
    }
}