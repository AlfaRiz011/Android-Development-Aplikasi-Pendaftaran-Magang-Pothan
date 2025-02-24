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
import com.example.capstone.R
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.data.request.LoginBodyRequest
import com.example.capstone.data.request.RegisterBodyRequest
import com.example.capstone.databinding.FragmentRegisterBinding
import com.example.capstone.ui.activity.HomeActivity
import com.example.capstone.ui.activity.HomeAdminActivity
import com.example.capstone.ui.viewmodel.AuthViewModel
import com.example.capstone.ui.viewmodel.factory.AuthViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showAuthLoading
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(requireActivity()) {
            binding.apply {
                showAuthLoading(progressBar, loadingText, it)
            }
        }

        setView()
        setOnBack()
    }

    private fun setView() {
        binding.apply {

            buttonRegister.setOnClickListener {
                val dataCheck = LoginBodyRequest(
                    email = emailInput.text.toString(),
                    password = passwordInput.text.toString()
                )

                viewModel.checkUser(dataCheck).observe(requireActivity()) { response ->
                    when (response.status) {
                        "success" -> {
                            val nextFragment = CompleteDataFragment().apply {
                                arguments = Bundle().apply {
                                    putString("email", dataCheck.email)
                                    putString("password", dataCheck.password)
                                }
                            }

                            parentFragmentManager.beginTransaction().apply {
                                setCustomAnimations(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left,
                                    R.anim.slide_in_left,
                                    R.anim.slide_out_right
                                )
                                replace(R.id.fragment_container_auth, nextFragment)
                                addToBackStack(null)
                                commit()
                            }
                        }

                        else -> handleError(requireContext(), response.message?.toInt())
                    }
                }
            }

            loginButton.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                    replace(R.id.fragment_container_auth, LoginFragment())
                    addToBackStack(null)
                    commit()
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