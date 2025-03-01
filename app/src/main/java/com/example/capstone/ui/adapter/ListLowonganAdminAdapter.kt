package com.example.capstone.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.data.model.JobApply
import com.example.capstone.data.model.Lowongan
import com.example.capstone.databinding.ItemLowonganBinding
import com.example.capstone.databinding.ItemVerifikasiRegistrasiBinding
import com.example.capstone.ui.activity.DetailActivity
import com.example.capstone.ui.activity.DetailRegistrasiActivity

class ListLowonganAdminAdapter(private val listRegistrasi: List<JobApply>) : RecyclerView.Adapter<ListLowonganAdminAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemVerifikasiRegistrasiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLowonganAdminAdapter.ItemViewHolder {
        val binding = ItemVerifikasiRegistrasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListLowonganAdminAdapter.ItemViewHolder, position: Int) {
        val regis = listRegistrasi[position]

        holder.binding.apply{
            namaPendaftar.text = regis.user?.nama
            divisi.text = regis.lowongan?.jabatan
            email.text = regis.user?.email
            phone.text = regis.user?.noTelp

            itemVerifRegister.setOnClickListener {
                val intent = Intent(root.context, DetailRegistrasiActivity::class.java).apply {
                    putExtra("regisId", regis.id.toString())
                }
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listRegistrasi.size

}