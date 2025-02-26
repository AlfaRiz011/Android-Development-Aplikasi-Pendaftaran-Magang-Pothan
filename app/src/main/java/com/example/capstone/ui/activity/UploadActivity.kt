package com.example.capstone.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstone.data.local.UserPreferences
import com.example.capstone.databinding.ActivityUploadBinding
import com.example.capstone.ui.viewmodel.UserViewModel
import com.example.capstone.ui.viewmodel.factory.UserViewModelFactory
import com.example.capstone.utils.Helper.createMultipartFromUri
import com.example.capstone.utils.Helper.handleError
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding

    private var selectedFilePart: MultipartBody.Part? = null
    private var selectedFileName: String? = null

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory.getInstance(this@UploadActivity)
    }

    private lateinit var pref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = viewModel.preferences

        lifecycleScope.launch {
            val userId = pref.getUser().first()?.id.toString()

            val title = intent.getStringExtra("title") ?: ""
            val jenisDokumen = intent.getStringExtra("jenisDokumen")?: ""

            setView(title, jenisDokumen, userId)
        }

    }

    private fun setView(title: String, jenisDokumen: String, userId: String) {
        binding.apply {
            dokumenTitle.text = title

            if(!selectedFileName.isNullOrEmpty() && selectedFilePart != null){
                namaDokumen.visibility = View.VISIBLE
            } else {
                namaDokumen.visibility = View.GONE
            }

            backButton.setOnClickListener {
                finish()
            }

            addFile.setOnClickListener {
                pickFile()
            }

            sendFile.setOnClickListener {
                upload(userId, jenisDokumen)
            }
        }
    }

    private fun upload(userId: String, jenisDokumen: String) {
        viewModel.uploadDocUser(userId, jenisDokumen, selectedFilePart).observe(this@UploadActivity){response ->
            when(response.status){
                "success" -> {
                    finish()
                }

                else -> handleError(this@UploadActivity, response.message?.toInt())
            }
        }
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        filePickerLauncher.launch(intent)
    }

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    handleFileSelection(uri)
                }
            }
        }

    private fun handleFileSelection(uri: Uri) {
        val (multipartBody, fileName) = createMultipartFromUri(this, uri)
        if (multipartBody != null && fileName != null) {
            binding.namaDokumen.visibility = View.VISIBLE
            selectedFilePart = multipartBody
            selectedFileName = fileName
            binding.namaDokumen.text = fileName
        }
    }
}