package com.example.capstone.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.R
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.databinding.FragmentVerifikasiRegistrasiBinding
import com.example.capstone.ui.activity.AuthActivity
import com.example.capstone.ui.adapter.ListDocumentAdminAdapter
import com.example.capstone.ui.adapter.ListLowonganAdminAdapter
import com.example.capstone.ui.viewmodel.DocumentViewModel
import com.example.capstone.ui.viewmodel.JobViewModel
import com.example.capstone.ui.viewmodel.factory.DocumentViewModelFactory
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.showLoading
import kotlinx.coroutines.launch

class VerifikasiRegistrasiFragment : Fragment() {

    private lateinit var binding: FragmentVerifikasiRegistrasiBinding

    private lateinit var adapter: ListLowonganAdminAdapter

    val viewModel: JobViewModel by activityViewModels {
        JobViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifikasiRegistrasiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (isAdded) {
                binding.apply {
                    showLoading(progressBar, loadingView, it)
                }
            }
        }
        pref = viewModel.preferences
        setLogout()
        setAdapter()
        setOnBack()
    }

    private fun setLogout() {
        binding.logo.setOnClickListener {
            lifecycleScope.launch {
                pref.logOut()

                val intent = Intent(requireContext(), AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun setAdapter() {
        viewModel.getAllRequested().observe(requireActivity()) { response ->
            if (!isAdded) return@observe

            when (response.status) {
                "success" -> {
                    val data = response.data

                    binding.apply {
                        if (data.isNullOrEmpty()) {
                            noData.visibility = View.VISIBLE
                        } else {
                            rvVerifikasiRegistrasi.visibility = View.VISIBLE
                            noData.visibility = View.GONE
                            if (!::adapter.isInitialized) {
                                adapter = ListLowonganAdminAdapter(data)
                                rvVerifikasiRegistrasi.layoutManager =
                                    LinearLayoutManager(requireContext())
                                rvVerifikasiRegistrasi.adapter = adapter
                            }
                        }
                    }
                }

                else -> Helper.handleError(requireContext(), response.message?.toInt())
            }
        }
    }

    private fun setOnBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}