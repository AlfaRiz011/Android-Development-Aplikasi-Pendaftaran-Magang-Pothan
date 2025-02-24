package com.example.capstone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.data.model.JobApply
import com.example.capstone.databinding.ItemVerifikasiDocsBinding

class ListDocumentAdminAdapter(private val listDocument: List<JobApply>) : RecyclerView.Adapter<ListDocumentAdminAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemVerifikasiDocsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDocumentAdminAdapter.ItemViewHolder {
        val binding = ItemVerifikasiDocsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListDocumentAdminAdapter.ItemViewHolder, position: Int) {
        val docs = listDocument[position]

    }

    override fun getItemCount(): Int = listDocument.size

}