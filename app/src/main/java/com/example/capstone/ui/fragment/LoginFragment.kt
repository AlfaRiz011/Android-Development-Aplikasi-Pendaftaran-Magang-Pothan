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
import com.example.capstone.databinding.FragmentLoginBinding
import com.example.capstone.ui.activity.HomeActivity
import com.example.capstone.ui.activity.HomeAdminActivity
import com.example.capstone.ui.viewmodel.AuthViewModel
import com.example.capstone.ui.viewmodel.factory.AuthViewModelFactory
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showAuthLoading
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref: UserPreferences

    private var email: String? = null
    private var password: String? = null
    private var role: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(requireActivity()) {
            binding.apply {
                showAuthLoading(progressBar, loadingText, it)
            }
        }

        pref = viewModel.preferences

        lifecycleScope.launch {
            role = pref.getRole().firstOrNull()
        }

        setView()
        setOnBack()
    }

    private fun setView() {
        binding.apply {
            emailInput.setText(email)
            passwordInput.setText(password)

            buttonLogin.setOnClickListener {
                val dataLogin = LoginBodyRequest(
                    email = emailInput.text.toString(),
                    password = passwordInput.text.toString()
                )

                viewModel.login(dataLogin).observe(requireActivity()) { response ->
                    when (response.status) {
                        "success" -> {
                            if (role == "user") {
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        HomeActivity::class.java
                                    ).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    })
                            } else {
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        HomeAdminActivity::class.java
                                    ).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    })
                            }

                            requireActivity().finish()
                        }

                        else -> handleError(requireContext(), response.message?.toInt())
                    }
                }

            }

            registerButton.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    replace(R.id.fragment_container_auth, RegisterFragment())
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