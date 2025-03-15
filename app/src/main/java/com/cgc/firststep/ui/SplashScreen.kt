package com.cgc.firststep.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cgc.firststep.databinding.ActivitySplashScreenBinding
import com.cgc.firststep.utils.Constant
import com.cgc.firststep.utils.MyAppPreference
import com.google.firebase.messaging.FirebaseMessaging

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private var fcmToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFirebaseNotificationToken()

        Handler(Looper.getMainLooper()).postDelayed({

     //       val intent = Intent(this@SplashScreen, PostApiExample::class.java)
     //       startActivity(intent)

            if(MyAppPreference.getBoolPreference(this@SplashScreen, Constant.IS_LOGIN)){
                startActivity(Intent(this, Dashboard::class.java))
            }else {
                startActivity(Intent(this, LoginScreen::class.java))
            }
            finish()

        }, 3000)

    }

    // Get Firebase Notification Token
    private fun getFirebaseNotificationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Get the token
                fcmToken = task.result
                Log.d("FCM Token", "Firebase Token: $fcmToken")

                // You can send the token to your server or save it for later use
            } else {
                Log.e("FCM Token", "Failed to retrieve token", task.exception)
            }
        }
    }
}