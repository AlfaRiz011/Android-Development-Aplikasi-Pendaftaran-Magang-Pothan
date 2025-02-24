package com.example.capstone.ui.fragment

import com.example.capstone.ui.viewmodel.JobViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.model.User
import com.example.capstone.databinding.FragmentStatusBinding
import com.example.capstone.ui.adapter.ListStatusUserAdapter
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper.handleError
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

        lifecycleScope.launch {
            val user = pref.getUser().firstOrNull()
            setAdapter(user)
        }
    }

    private fun setAdapter(user: User?) {
        val userId = user?.id.toString()
        viewModel.getRegisteredJobs(userId).observe(requireActivity()){ response ->
            when (response.status){
                "success" -> {
                    val data = response.data

                    binding.apply {
                        if (data.isNullOrEmpty()) {
                            noData.visibility = View.VISIBLE
                            rvStatus.visibility = View.GONE
                        } else {
                            rvStatus.visibility = View.VISIBLE
                            noData.visibility = View.GONE
                            adapter = ListStatusUserAdapter(data)
                            val layoutManager = LinearLayoutManager(requireContext())
                            rvStatus.layoutManager = layoutManager
                            rvStatus.adapter = adapter
                        }
                    }
                }

                else -> handleError(requireContext(), response.message?.toInt())
            }
        }
    }
}