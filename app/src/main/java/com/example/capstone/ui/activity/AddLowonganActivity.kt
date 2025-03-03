package com.example.capstone.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.data.request.JobBodyRequest
import com.example.capstone.databinding.ActivityAddLowonganBinding
import com.example.capstone.ui.viewmodel.JobViewModel
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper.handleError
import java.util.Calendar

class AddLowonganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddLowonganBinding

    private val viewModel: JobViewModel by viewModels {
        JobViewModelFactory.getInstance(this@AddLowonganActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.apply {

            backButton.setOnClickListener {
                finish()
            }

            tutupInput.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePicker = DatePickerDialog(
                    this@AddLowonganActivity,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        tutupInput.setText(formattedDate)
                    },
                    year,
                    month,
                    day
                )

                datePicker.show()
            }


            buttonAddLowongan.setOnClickListener {
                val data = JobBodyRequest(
                    posisi = poisiInput.text.toString(),
                    jabatan = divisiInput.text.toString(),
                    closedAt = tutupInput.text.toString(),
                    periodeMagang = periodeInput.text.toString(),
                    deskripsi = deskripsiInput.text.toString()
                )

                viewModel.createJob(data).observe(this@AddLowonganActivity) { response ->
                    when (response.status) {
                        "success" -> {
                            val intent =
                                Intent(this@AddLowonganActivity, HomeAdminActivity::class.java)
                            intent.putExtra("UPLOAD_SUCCESS", true)
                            startActivity(intent)
                            finish()
                        }

                        else -> handleError(this@AddLowonganActivity, response.message?.toInt())
                    }
                }
            }
        }
    }
}