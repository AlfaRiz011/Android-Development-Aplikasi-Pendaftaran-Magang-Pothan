package com.example.capstone.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstone.R
import com.example.capstone.databinding.ActivityHomeBinding
import com.example.capstone.ui.fragment.LowonganFragment
import com.example.capstone.ui.fragment.ProfileFragment
import com.example.capstone.ui.fragment.StatusFragment
import com.example.capstone.utils.Helper.showToast

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uploadSuccess = intent.getBooleanExtra("UPLOAD_SUCCESS", false)

        setOnBackPressedHandler()
        setupNavBar()
        setFragment(LowonganFragment())
        checkUpload(uploadSuccess)
    }

    private fun checkUpload(uploadSuccess: Boolean) {
        if (uploadSuccess) {
            setFragment(ProfileFragment())
            showToast(this@HomeActivity, "Dokumen berhasil diunggah")
        }
    }

    private fun setupNavBar() {
        binding.navBottomUser.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_job -> setFragment(LowonganFragment())
                R.id.menu_status -> setFragment(StatusFragment())
                R.id.menu_profile -> setFragment(ProfileFragment())
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