package com.example.capstone.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.data.model.Document
import com.example.capstone.databinding.ActivityDetailRegistrasiBinding
import com.example.capstone.ui.viewmodel.DocumentViewModel
import com.example.capstone.ui.viewmodel.JobViewModel
import com.example.capstone.ui.viewmodel.factory.DocumentViewModelFactory
import com.example.capstone.ui.viewmodel.factory.JobViewModelFactory
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.openOrDownloadFile
import com.example.capstone.utils.Helper.showToast

class DetailRegistrasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRegistrasiBinding

    private var regisId: String? = null

    private val viewModel: JobViewModel by viewModels {
        JobViewModelFactory.getInstance(this@DetailRegistrasiActivity)
    }

    private val docViewModel: DocumentViewModel by viewModels {
        DocumentViewModelFactory.getInstance(this@DetailRegistrasiActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        regisId = intent.getStringExtra("regisId") ?: ""

        setView()
    }

    private fun setView() {
        regisId?.let {
            viewModel.getVerifiedRegistration(it).observe(this) { response ->
                when (response.status) {
                    "success" -> {
                        binding.apply {
                            val data = response.data
                            if (data != null) {
                                namaValue.text = data.user?.nama
                                posisiValue.text = data.lowongan?.posisi

                                setActionButton(data.userId.toString())
                            }
                        }
                    }

                    else -> handleError(this, response.message?.toInt())
                }
            }
        }
    }

    private fun setActionButton(userId: String) {
        docViewModel.getUserDocuments(userId).observe(this) { response ->
            when (response.status) {
                "success" -> {
                    binding.apply {
                        val documents = response.data.orEmpty()

                        fun getLatestDocument(type: String) = documents
                            .filter {
                                it.jenisDokumen.equals(
                                    type,
                                    ignoreCase = true
                                ) && it.status.equals("diterima", ignoreCase = true)
                            }
                            .maxByOrNull { it.uploadedAt.toString() }

                        val docMap = mapOf(
                            buttonKtp to getLatestDocument("ktp"),
                            buttonKtm to getLatestDocument("ktm"),
                            buttonSuratPengantar to getLatestDocument("surat pengantar"),
                            buttonCv to getLatestDocument("cv")
                        )

                        docMap.forEach { (button, document) ->
                            button.setOnClickListener { openUserDocument(document) }
                        }
                    }
                }

                else -> handleError(this, response.message?.toInt())
            }
        }

        binding.apply {
            buttonDiterima.setOnClickListener {
                approve()
            }

            buttonDitolak.setOnClickListener {
                reject()
            }
        }
    }

    private fun reject() {
        regisId?.let {
            viewModel.rejectJobRegistration(it).observe(this) { response ->
                when (response.status) {
                    "success" -> {
                        binding.apply {
                            finish()
                        }
                    }

                    else -> handleError(this, response.message?.toInt())
                }
            }
        }
    }

    private fun approve() {
        regisId?.let {
            viewModel.verifyJobRegistration(it).observe(this) { response ->
                when (response.status) {
                    "success" -> {
                        binding.apply {
                            finish()
                        }
                    }

                    else -> handleError(this, response.message?.toInt())
                }
            }
        }
    }

    private fun openUserDocument(document: Document?) {
        document?.filePath?.let { filePath ->
            openOrDownloadFile(this, filePath)
        } ?: showToast(this, "Dokumen tidak tersedia")
    }
}

