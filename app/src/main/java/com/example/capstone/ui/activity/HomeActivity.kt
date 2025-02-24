package com.example.capstone.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.capstone.R
import com.example.capstone.databinding.ActivityHomeBinding
import com.example.capstone.ui.fragment.LowonganFragment
import com.example.capstone.ui.fragment.ProfileFragment
import com.example.capstone.ui.fragment.StatusFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnBackPressedHandler()
        setupNavBar()
        setFragment(LowonganFragment())
    }

    private fun setupNavBar() {
        binding.navBottomUser.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_job -> setFragment(LowonganFragment())
                R.id.nav_status -> setFragment(StatusFragment())
                R.id.nav_profile -> setFragment(ProfileFragment())
            }
            true
        }
    }

    private fun setOnBackPressedHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
        if (currentFragment?.javaClass != fragment.javaClass) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, fragment)
                .commit()
        }
    }
}