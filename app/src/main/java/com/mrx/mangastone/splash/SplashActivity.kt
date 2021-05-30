package com.mrx.mangastone.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.mrx.mangastone.MainActivity
import com.mrx.mangastone.R
import com.mrx.mangastone.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.logo.startAnimation(fadeIn)
        binding.appName.startAnimation(fadeIn)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}