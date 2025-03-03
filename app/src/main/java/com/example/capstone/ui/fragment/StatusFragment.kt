package com.example.capstone.ui.fragment

import com.example.capstone.ui.viewmodel.JobViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.User
import com.example.capstone.databinding.FragmentStatusBinding
import com.example.capstone.ui.adapter.ListStatusUserAdapter
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showLoading
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class StatusFragment : Fragment() {

    private lateinit var binding: FragmentStatusBinding

    private lateinit var adapter: ListStatusUserAdapter

    private val viewModel : JobViewModel by activityViewModels {
        JobViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref : UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = viewModel.preferences

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (isAdded) {
                binding.apply {
                    showLoading(progressBar, loadingView, it)
                }
            }
        }

        lifecycleScope.launch {
            val user = pref.getUser().firstOrNull()
            setAdapter(user)
            setOnBack()
        }
    }

    private fun setAdapter(user: User?) {
        val userId = user?.id.toString()
        viewModel.getRegisteredJobs(userId).observe(viewLifecycleOwner) { response ->
            if (!isAdded) return@observe

            when (response.status) {
                "success" -> {
                    val data = response.data

                    binding.apply {
                        if (data.isNullOrEmpty()) {
                            noData.visibility = View.VISIBLE
                        } else {
                            noData.visibility = View.GONE
                            rvStatus.visibility = View.VISIBLE
                            if (!::adapter.isInitialized) {
                                adapter = ListStatusUserAdapter(data)
                                rvStatus.layoutManager = LinearLayoutManager(requireContext())
                                rvStatus.adapter = adapter
                            }
                        }
                    }
                }
                else -> {
                    if (isAdded) {
                        handleError(requireContext(), response.message?.toInt())
                    }
                }
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