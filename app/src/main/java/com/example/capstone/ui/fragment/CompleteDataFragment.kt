package com.example.capstone.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.example.capstone.R
import com.example.capstone.data.request.RegisterBodyRequest
import com.example.capstone.databinding.FragmentCompleteDataBinding
import com.example.capstone.ui.viewmodel.AuthViewModel
import com.example.capstone.ui.viewmodel.factory.AuthViewModelFactory
import com.example.capstone.utils.Helper
import java.util.Calendar

class CompleteDataFragment : Fragment() {

    private lateinit var binding: FragmentCompleteDataBinding

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private var email: String? = null
    private var password: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompleteDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = arguments?.getString("email")
        password = arguments?.getString("password")

        setView()
    }

    private fun setView() {
        binding.apply {

            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

            tanggalLahirInput.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePicker = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        tanggalLahirInput.setText(formattedDate)
                    },
                    year,
                    month,
                    day
                )

                datePicker.show()
            }

            val dataRegister = RegisterBodyRequest(
                email = email,
                password = password,
                nim = nimInput.text.toString(),
                nama = namaInput.text.toString(),
                universitas = asalKampusInput.text.toString(),
                noTelp = noHandponeInput.text.toString(),
                tanggalLahir = tanggalLahirInput.text.toString(),
                alamat = alamatInput.text.toString()
            )

            viewModel.register(dataRegister).observe(requireActivity()) { response ->
                when (response.status) {
                    "success" -> {
                        val nextFragment = LoginFragment().apply {
                            arguments = Bundle().apply {
                                putString("email", email)
                                putString("password", password)
                            }
                        }

                        parentFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.slide_in_left,
                                R.anim.slide_out_right
                            )
                            .replace(R.id.fragment_container_auth, nextFragment)
                            .addToBackStack(null)
                            .commit()
                    }

                    else -> Helper.handleError(requireContext(), response.message?.toInt())
                }
            }
        }
    }
}