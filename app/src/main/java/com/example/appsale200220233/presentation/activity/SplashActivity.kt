package com.example.appsale200220233.presentation.activity

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.example.appsale200220233.R
import com.example.appsale200220233.common.AppConstant
import com.example.appsale200220233.data.local.SharePreferenceApp

class SplashActivity : AppCompatActivity() {

    private lateinit var lottieAnimationView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lottieAnimationView = findViewById(R.id.splash_view)

        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { }
            override fun onAnimationCancel(animation: Animator) { }
            override fun onAnimationRepeat(animation: Animator) { }
            override fun onAnimationEnd(animation: Animator) {
                val token = SharePreferenceApp.getStringValue(this@SplashActivity, AppConstant.TOKEN_KEY)
                val intent: Intent
                if (token.isNullOrEmpty()) {
                    intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    intent = Intent(this@SplashActivity, ProductActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        })
    }
}
