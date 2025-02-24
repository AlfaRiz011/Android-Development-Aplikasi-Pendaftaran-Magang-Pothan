package com.example.capstone.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.capstone.R

object Helper {

    fun showLoading(progressBar: ProgressBar, isLoading: Boolean) {
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

}