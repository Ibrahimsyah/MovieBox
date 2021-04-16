package com.zairussalamdev.moviebox.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.moviebox.R
import com.zairussalamdev.moviebox.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    companion object {
        const val SPLASH_SCREEN_DELAY_MILLS = 2500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_SCREEN_DELAY_MILLS)
    }
}