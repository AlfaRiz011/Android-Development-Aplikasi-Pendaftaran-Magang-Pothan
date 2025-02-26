package com.example.capstone.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.local.dataStore
import com.example.capstone.databinding.ActivitySplashscreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {

        val pref = UserPreferences.getInstance(this.dataStore)

        lifecycleScope.launch {
            delay(5000)

            val isSession = pref.getSession().first()
            val role = pref.getRole().first()

            val nextActivity = when {
                !isSession -> AuthActivity::class.java
                role == "user" -> HomeActivity::class.java
                else -> HomeAdminActivity::class.java
            }

            startActivity(Intent(this@SplashscreenActivity, nextActivity))

            finish()
        }
    }
}