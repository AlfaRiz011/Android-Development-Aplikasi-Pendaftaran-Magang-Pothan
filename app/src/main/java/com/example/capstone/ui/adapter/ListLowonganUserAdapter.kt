package com.example.capstone.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.data.model.Lowongan
import com.example.capstone.databinding.ItemLowonganBinding
import com.example.capstone.ui.activity.DetailActivity

class ListLowonganUserAdapter(private var listJob: List<Lowongan>) : RecyclerView.Adapter<ListLowonganUserAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemLowonganBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLowonganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val job = listJob[position]

        holder.binding.apply {
            namaPosisi.text = job.posisi
            divisi.text = job.jabatan
            periode.text = job.periodeMagang
            closeDate.text = job.closedAt

            itemJob.setOnClickListener {
                val intent = Intent(root.context, DetailActivity::class.java).apply {
                    putExtra("jobId", job.id.toString())
                }
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listJob.size
}