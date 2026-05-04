package com.example.spoton

import android.app.Application
import com.google.firebase.FirebaseApp

class SpotOnApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
