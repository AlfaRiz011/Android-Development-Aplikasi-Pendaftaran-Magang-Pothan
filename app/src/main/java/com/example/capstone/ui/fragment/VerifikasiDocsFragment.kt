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
import com.example.capstone.databinding.FragmentVerifikasiDocsBinding
import com.example.capstone.ui.activity.AuthActivity
import com.example.capstone.ui.adapter.ListDocumentAdminAdapter
import com.example.capstone.ui.adapter.ListLowonganUserAdapter
import com.example.capstone.ui.viewmodel.DocumentViewModel
import com.example.capstone.ui.viewmodel.JobViewModel
import com.example.capstone.ui.viewmodel.factory.DocumentViewModelFactory
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showAdminLoading
import com.example.capstone.utils.Helper.showLoading
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class VerifikasiDocsFragment : Fragment() {

    private lateinit var binding: FragmentVerifikasiDocsBinding

    private lateinit var adapter: ListDocumentAdminAdapter

    private val viewModel: DocumentViewModel by activityViewModels {
        DocumentViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifikasiDocsBinding.inflate(layoutInflater)
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
        viewModel.getAllDocument().observe(requireActivity()) { response ->
            if (!isAdded) return@observe

            when (response.status) {
                "success" -> {
                    val data = response.data

                    binding.apply {
                        if (data.isNullOrEmpty()) {
                            noData.visibility = View.VISIBLE
                        } else {
                            rvVerifikasiDocs.visibility = View.VISIBLE
                            noData.visibility = View.GONE
                            if (!::adapter.isInitialized) {
                                adapter = ListDocumentAdminAdapter(data)
                                rvVerifikasiDocs.layoutManager =
                                    LinearLayoutManager(requireContext())
                                rvVerifikasiDocs.adapter = adapter
                            }
                        }
                    }
                }

                else -> handleError(requireContext(), response.message?.toInt())
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