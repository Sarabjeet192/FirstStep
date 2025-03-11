package com.cgc.firststep.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cgc.firststep.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this, PostApiExample::class.java))
//            if(MyAppPreference.getBoolPreference(this@SplashScreen, Constant.IS_LOGIN)){
//                startActivity(Intent(this, Dashboard::class.java))
//            }else {
//                startActivity(Intent(this, LoginScreen::class.java))
//            }
            finish()

        }, 2000)

    }
}