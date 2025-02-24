package com.example.capstone.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.data.model.JobApply
import com.example.capstone.data.model.Lowongan
import com.example.capstone.databinding.ItemLowonganBinding
import com.example.capstone.databinding.ItemStatusBinding
import com.example.capstone.ui.activity.DetailActivity

class ListStatusUserAdapter(private val listRegistrasi: List<JobApply>) : RecyclerView.Adapter<ListStatusUserAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStatusUserAdapter.ItemViewHolder {
        val binding = ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListStatusUserAdapter.ItemViewHolder, position: Int) {
        val regis = listRegistrasi[position]

        holder.binding.apply {
            namaPosisi.text = regis.lowongan?.posisi.toString()
            divisi.text = regis.lowongan?.jabatan.toString()
            periode.text = regis.lowongan?.periodeMagang.toString()
            closeDate.text = regis.lowongan?.closedAt.toString()

            fun View.setVisible(visible: Boolean) {
                visibility = if (visible) View.VISIBLE else View.GONE
            }

            val isPending = regis.status == "pending"
            val isDiterima = regis.status == "diterima"
            val isDitolak = !isPending && !isDiterima

            pending.setVisible(isPending)
            iconPending.setVisible(isPending)
            diterima.setVisible(isDiterima)
            iconDiterima.setVisible(isDiterima)
            ditolak.setVisible(isDitolak)
            iconDitolak.setVisible(isDitolak)


            itemStatus.setOnClickListener {
                val intent = Intent(root.context, DetailActivity::class.java).apply {
                    putExtra("jobId", regis.jobId.toString())
                }

                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listRegistrasi.size

}