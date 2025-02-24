package com.example.capstone.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstone.R
import com.example.capstone.databinding.ActivityHomeAdminBinding
import com.example.capstone.ui.fragment.LowonganFragment
import com.example.capstone.ui.fragment.ProfileFragment
import com.example.capstone.ui.fragment.StatusFragment
import com.example.capstone.ui.fragment.VerifikasiDocsFragment
import com.example.capstone.ui.fragment.VerifikasiRegistrasiFragment

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnBackPressedHandler()
        setupNavBar()
        setFragment(VerifikasiRegistrasiFragment())
    }

    private fun setupNavBar() {
        binding.navBottomAdmin.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_job_admin -> setFragment(VerifikasiRegistrasiFragment())
                R.id.menu_document -> setFragment(VerifikasiDocsFragment())
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
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerAdmin.id)
        if (currentFragment?.javaClass != fragment.javaClass) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerAdmin.id, fragment)
                .commit()
        }
    }
}