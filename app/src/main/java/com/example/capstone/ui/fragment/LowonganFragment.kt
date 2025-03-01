package com.example.capstone.ui.fragment

import com.example.capstone.ui.viewmodel.JobViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.databinding.FragmentLowonganBinding
import com.example.capstone.ui.adapter.ListLowonganUserAdapter
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showLoading

class LowonganFragment : Fragment() {

    private lateinit var binding: FragmentLowonganBinding

    private lateinit var adapter: ListLowonganUserAdapter

    private val viewModel : JobViewModel by activityViewModels {
        JobViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLowonganBinding.inflate(layoutInflater)
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

        setAdapter()
    }

    private fun setAdapter() {
        viewModel.getAllJobs().observe(viewLifecycleOwner) { response ->
            if (!isAdded) return@observe

            when (response.status) {
                "success" -> {
                    val data = response.data
                    binding.apply {
                        if (data.isNullOrEmpty()) {
                            noData.visibility = View.VISIBLE
                            rvLowongan.visibility = View.GONE
                        } else {
                            noData.visibility = View.GONE
                            rvLowongan.visibility = View.VISIBLE

                            if (!::adapter.isInitialized) {
                                adapter = ListLowonganUserAdapter(data)
                                rvLowongan.layoutManager = LinearLayoutManager(requireContext())
                                rvLowongan.adapter = adapter
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
}