package com.example.bookwise

import android.app.Application
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.init(this)
    }
}