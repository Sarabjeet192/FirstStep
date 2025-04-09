package com.cgc.firststep.utils

import android.app.Application
import android.util.Log
import com.facebook.FacebookSdk
import com.google.firebase.FirebaseApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FacebookSdk.sdkInitialize(this)

    }

}
