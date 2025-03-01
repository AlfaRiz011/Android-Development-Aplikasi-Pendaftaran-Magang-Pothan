package com.example.capstone.utils

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.capstone.R
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Environment

object Helper {

    fun showLoading(progressBar: ProgressBar, loadingView: View, isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun showAdminLoading(progressBar: ProgressBar, isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun showAuthLoading(progressBar: ProgressBar, loadingText: TextView, isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        loadingText.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun handleError(context: Context, error: Int?) {
        val errorMessage = when (error) {
            400 -> context.getString(R.string.error_invalid_input)
            401 -> context.getString(R.string.error_unauthorized_401)
            500, 503 -> context.getString(R.string.error_server_500)
            else -> context.getString(R.string.error_unknown)
        }

        showToast(context, errorMessage)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
        var name = "document.pdf"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    name = cursor.getString(index)
                }
            }
        }
        return name
    }

    fun createMultipartFromUri(context: Context, uri: Uri): Pair<MultipartBody.Part?, String?> {
        val fileName = getFileName(context.contentResolver, uri)
        val file = File(context.cacheDir, fileName)
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            val requestBody = RequestBody.create("application/pdf".toMediaTypeOrNull(), file)
            val multipartBody = MultipartBody.Part.createFormData("document", file.name, requestBody)

            Pair(multipartBody, fileName)
        } catch (e: Exception) {
            Log.e("FileHelper", "Error processing file", e)
            Pair(null, null)
        }
    }

    fun openOrDownloadFile(context: Context, filePath: String) {
        val baseUrl = "http://192.168.1.7:5000"

        val normalizedPath = filePath.replace("\\", "/")
        val fullUrl = "$baseUrl/$normalizedPath"

        val uri = Uri.parse(fullUrl)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showDownloadDialog(context, fullUrl)
        }
    }


    private fun showDownloadDialog(context: Context, fileUrl: String) {
        AlertDialog.Builder(context)
            .setTitle("Unduh Dokumen")
            .setMessage("Tidak ada aplikasi untuk membuka file PDF. Ingin mengunduhnya?")
            .setPositiveButton("Unduh") { _, _ -> downloadFile(context, fileUrl) }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun downloadFile(context: Context, fileUrl: String) {
        val request = DownloadManager.Request(Uri.parse(fileUrl)).apply {
            setTitle("Mengunduh Dokumen")
            setDescription("Mengunduh file PDF...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "dokumen.pdf")
        }

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Mengunduh file...", Toast.LENGTH_SHORT).show()
    }
}