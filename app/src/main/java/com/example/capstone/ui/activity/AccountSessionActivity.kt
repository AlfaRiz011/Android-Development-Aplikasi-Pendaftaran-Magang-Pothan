package com.example.capstone.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.databinding.ActivityAccountSessionBinding
import com.example.capstone.ui.viewmodel.AuthViewModel
import com.example.capstone.ui.viewmodel.UserViewModel
import com.example.capstone.ui.viewmodel.factory.AuthViewModelFactory
import com.example.capstone.ui.viewmodel.factory.UserViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccountSessionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAccountSessionBinding

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(this@AccountSessionActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = viewModel.preferences

        lifecycleScope.launch {
            delay(5000)

            val role = pref.getRole().first()

            val nextActivity = when (role) {
                "user" -> HomeActivity::class.java
                "admin" -> HomeAdminActivity::class.java
                else -> AuthActivity::class.java
            }

            startActivity(Intent(this@AccountSessionActivity, nextActivity))

            finish()
        }
    }
}