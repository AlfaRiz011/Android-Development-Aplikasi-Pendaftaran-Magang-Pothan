package com.example.capstone.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.User
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.ui.activity.DocumentActivity
import com.example.capstone.ui.adapter.ListStatusUserAdapter
import com.example.capstone.ui.viewmodel.UserViewModel
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.ui.viewmodel.factory.UserViewModelFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
 

    private val viewModel : UserViewModel by activityViewModels {
        UserViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref : UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = viewModel.preferences

        lifecycleScope.launch {
            val user = pref.getUser().firstOrNull()

            setView(user)
        }
    }

    private fun setView(user: User?) {
        binding.apply {
            email.text = user?.email
            nama.text = user?.nama
            noTelpon.text = user?.noTelp
            alamat.text = user?.alamat
            asalKampus.text = user?.universitas
            nim.text = user?.nim
            tanggalLahir.text = user?.tanggalLahir

            buttonDocument.setOnClickListener {
                val intent = Intent(requireActivity(), DocumentActivity::class.java)
                intent.putExtra("userId", user?.id.toString())
                startActivity(intent)
            }
        }
    }
}