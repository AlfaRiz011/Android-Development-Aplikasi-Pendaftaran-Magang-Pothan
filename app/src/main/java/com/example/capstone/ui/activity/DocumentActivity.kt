package com.example.capstone.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.capstone.R
import com.example.capstone.data.model.Document
import com.example.capstone.databinding.ActivityDocumentBinding
import com.example.capstone.ui.viewmodel.DocumentViewModel
import com.example.capstone.ui.viewmodel.factory.DocumentViewModelFactory
import com.example.capstone.utils.Helper.handleError
import com.example.capstone.utils.Helper.showAdminLoading

class DocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentBinding

    private val viewModel: DocumentViewModel by viewModels {
        DocumentViewModelFactory.getInstance(this@DocumentActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this@DocumentActivity) {
            binding.apply {
                showAdminLoading(progressBar, it)
            }
        }
        val userId = intent.getStringExtra("userId") ?: ""
        setView(userId)
    }

    private fun setView(userId: String) {
        viewModel.getUserDocuments(userId).observe(this@DocumentActivity) { response ->
            when (response.status) {
                "success" -> {
                    val documents = response.data ?: emptyList()

                    // Filter dokumen KTP dan ambil dokumen terbaru
                    val ktpDocument = documents
                        .filter { it.jenisDokumen.equals("ktp", ignoreCase = true) }
                        .maxByOrNull { it.uploadedAt.toString() }
                    setDocumentView(
                        ktpDocument,
                        binding.addKtp,
                        binding.ktpValue,
                        binding.iconStatusKtp,
                        "KTP",
                        "ktp"
                    )

                    // Filter dokumen KTM dan ambil dokumen terbaru
                    val ktmDocument = documents
                        .filter { it.jenisDokumen.equals("ktm", ignoreCase = true) }
                        .maxByOrNull { it.uploadedAt.toString() }
                    setDocumentView(
                        ktmDocument,
                        binding.addKtm,
                        binding.ktmValue,
                        binding.iconStatusKtm,
                        "KTM",
                        "ktm"
                    )

                    // Filter dokumen Surat Pengantar dan ambil dokumen terbaru
                    val suratPengantarDocument = documents
                        .filter { it.jenisDokumen.equals("surat pengantar", ignoreCase = true) }
                        .maxByOrNull { it.uploadedAt.toString() }
                    setDocumentView(
                        suratPengantarDocument,
                        binding.addSuratPengantar,
                        binding.suratPengantarValue,
                        binding.iconStatusSuratPengantar,
                        "SURAT PENGANTAR",
                        "surat pengantar"
                    )

                    // Filter dokumen CV dan ambil dokumen terbaru
                    val cvDocument = documents
                        .filter { it.jenisDokumen.equals("cv", ignoreCase = true) }
                        .maxByOrNull { it.uploadedAt.toString() }
                    setDocumentView(
                        cvDocument,
                        binding.addCv,
                        binding.cvValue,
                        binding.iconStatusCv,
                        "CV",
                        "cv"
                    )
                }
                else -> handleError(this@DocumentActivity, response.message?.toInt())
            }
        }
    }

    private fun setDocumentView(
        document: Document?,
        addButton: View,
        valueTextView: View,
        iconImageView: View,
        title: String,
        jenisDokumen: String
    ) {
        binding.apply {
            if (document != null) {
                addButton.visibility = if (document.status == "ditolak") View.VISIBLE else View.GONE
                (valueTextView as? AppCompatTextView)?.text = document.filePath

                val (iconRes, bgColor) = when (document.status) {
                    "pending" -> R.drawable.icon_pending to R.color.yellow
                    "diterima" -> R.drawable.icon_approve to R.color.green
                    "ditolak" -> R.drawable.icon_reject to R.color.red
                    else -> null to null
                }

                iconRes?.let { (iconImageView as? AppCompatImageView)?.setImageResource(it) }
                bgColor?.let {
                    (iconImageView as? AppCompatImageView)?.backgroundTintList =
                        ContextCompat.getColorStateList(root.context, it)
                }
                iconImageView.visibility = View.VISIBLE
            } else {
                addButton.visibility = View.VISIBLE
                (valueTextView as? AppCompatTextView)?.text = getString(R.string.no_data)
                iconImageView.visibility = View.GONE
            }

            addButton.setOnClickListener {
                directToUploadActivity(title, jenisDokumen)
            }
        }
    }


    private fun directToUploadActivity(title: String, jenisDokumen: String) {
        val intent = Intent(this@DocumentActivity, UploadActivity::class.java).apply {
            putExtra("title", title)
            putExtra("jenisDokumen", jenisDokumen)
        }
        startActivity(intent)
    }

}
