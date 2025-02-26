package com.example.capstone.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.databinding.ActivityDetailBinding
import com.example.capstone.ui.viewmodel.JobViewModel
import com.example.capstone.ui.viewmodel.UserViewModel
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.ui.viewmodel.factory.UserViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showLoading
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var jobId: String? = null

    private val viewModel: JobViewModel by viewModels {
        JobViewModelFactory.getInstance(this@DetailActivity)
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory.getInstance(this)
    }

    private lateinit var pref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = viewModel.preferences

        jobId = intent.getStringExtra("jobId") ?: ""

        lifecycleScope.launch {
            val userId = pref.getUser().firstOrNull()?.id.toString()

            viewModel.isLoading.observe(this@DetailActivity) {
                binding.apply {
                    showLoading(progressBar, loadingView, it)
                }
            }

            userViewModel.isLoading.observe(this@DetailActivity) {
                binding.apply {
                    showLoading(progressBar, loadingView, it)
                }
            }

            setView(jobId, userId)
            setActionButtons(jobId, userId)
        }
    }

    private fun setActionButtons(jobId: String?, userId: String?) {
        binding.apply {
            if (jobId != null && userId != null) {
                viewModel.getRegisteredJobs(userId).observe(this@DetailActivity) { response ->
                    when (response.status) {
                        "success" -> {
                            response.data?.let { responseData ->
                                val job = responseData.find { it.jobId.toString() == jobId }

                                binding.apply {
                                    when (job?.status) {
                                        "diterima" -> {
                                            pending.visibility = View.GONE
                                            buttonDaftar.visibility = View.GONE
                                            diterima.visibility = View.VISIBLE
                                        }

                                        "pending" -> {
                                            diterima.visibility = View.GONE
                                            buttonDaftar.visibility = View.GONE
                                            pending.visibility = View.VISIBLE
                                        }

                                        else -> {
                                            diterima.visibility = View.GONE
                                            pending.visibility = View.GONE
                                            buttonDaftar.visibility = View.VISIBLE
                                        }
                                    }
                                }
                            } ?: run {
                                diterima.visibility = View.GONE
                                pending.visibility = View.GONE
                                buttonDaftar.visibility = View.VISIBLE
                            }
                        }

                        else -> handleError(this@DetailActivity, response.message?.toInt())
                    }
                }
            }

            buttonDaftar.setOnClickListener {
                registrasi(userId, jobId)
            }
        }
    }

    private fun setView(jobId: String?, userId: String?) {
        binding.apply {
            if (jobId != null && userId != null) {
                viewModel.getJobById(jobId).observe(this@DetailActivity) { response ->
                    when (response.status) {
                        "success" -> {
                            if (response.data != null) {
                                val job = response.data

                                namaPosisi.text = job.posisi
                                divisi2.text = job.jabatan
                                periodeValue.text = job.periodeMagang
                                waktuTutupValue.text = job.closedAt
                                deskripsiValue.text = job.deskripsi
                            }
                        }

                        else -> handleError(this@DetailActivity, response.message?.toInt())
                    }
                }
            }
        }
    }

    private fun registrasi(userId: String?, jobId: String?) {
        if (userId != null && jobId != null) {
            userViewModel.applyJobUser(userId, jobId).observe(this@DetailActivity){response ->
                when(response.status){
                    "success" ->{
                        val refreshIntent = Intent(this, DetailActivity::class.java).apply {
                            putExtra("jobId", jobId)
                        }
                        finish()
                        startActivity(refreshIntent)
                    }

                    else -> handleError(this@DetailActivity, response.message?.toInt())
                }
            }
        }
    }
}