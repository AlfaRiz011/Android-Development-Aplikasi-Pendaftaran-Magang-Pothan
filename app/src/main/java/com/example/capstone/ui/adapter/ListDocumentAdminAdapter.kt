package com.example.capstone.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.data.model.Document
import com.example.capstone.data.model.JobApply
import com.example.capstone.databinding.ItemVerifikasiDocsBinding
import com.example.capstone.ui.activity.DetailActivity
import com.example.capstone.ui.activity.DetailDocsActivity

class ListDocumentAdminAdapter(private val listDocument: List<Document>) : RecyclerView.Adapter<ListDocumentAdminAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemVerifikasiDocsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDocumentAdminAdapter.ItemViewHolder {
        val binding = ItemVerifikasiDocsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListDocumentAdminAdapter.ItemViewHolder, position: Int) {
        val docs = listDocument[position]

        holder.binding.apply {
            namaPendaftar.text = docs.user?.nama
            dokumen.text = docs.jenisDokumen
            email.text = docs.user?.email
            phone.text = docs.user?.noTelp
            itemVerifDocs.setOnClickListener {
                val intent = Intent(root.context, DetailDocsActivity::class.java).apply {
                    putExtra("docsId", docs.id.toString())
                }
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listDocument.size

}