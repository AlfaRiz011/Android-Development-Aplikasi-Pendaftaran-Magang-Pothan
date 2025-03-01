package com.example.capstone.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivityDetailDocsBinding
import com.example.capstone.ui.viewmodel.DocumentViewModel
import com.example.capstone.ui.viewmodel.factory.DocumentViewModelFactory
import com.example.capstone.utils.Helper
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.openOrDownloadFile

class DetailDocsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDocsBinding

    private var docsId: String? = null

    private val viewModel: DocumentViewModel by viewModels {
        DocumentViewModelFactory.getInstance(this@DetailDocsActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailDocsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        docsId = intent.getStringExtra("docsId") ?: ""

        setView()
    }

    private fun setView() {
        docsId?.let { it ->
            viewModel.getVerifiedDocument(it).observe(this) { response ->
                when (response.status) {
                    "success" -> {
                        val data = response.data

                        binding.apply {
                            if (data != null) {
                                namaLengkap.text = data.user?.nama
                                email.text = data.user?.email
                                phone.text = data.user?.noTelp
                                dokumen.text = data.jenisDokumen

                                buttonFile.setOnClickListener{
                                    data.filePath?.let { filePath ->
                                        openOrDownloadFile(this@DetailDocsActivity, filePath)
                                    }
                                }

                                buttonDitolak.setOnClickListener {
                                    reject()
                                }

                                buttonDiterima.setOnClickListener {
                                    approve()
                                }
                            }
                        }
                    }

                    else -> handleError(this, response.message?.toInt())
                }
            }
        }
    }

    private fun reject() {
        docsId?.let {
            viewModel.rejectDocument(it).observe(this) { response ->
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
        docsId?.let {
            viewModel.verifyDocument(it).observe(this) { response ->
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
}