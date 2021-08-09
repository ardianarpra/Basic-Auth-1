package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX

class SubServiceAdapter(private val subLayanan: List<DataXX>): RecyclerView.Adapter<SubServiceAdapter.SubServiceViewHolder>() {

    class SubServiceViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubServiceViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return SubServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubServiceViewHolder, position: Int) {
        with(holder){
            with(subLayanan[position]){
                if (gambar != null){
                    Picasso.get().load("https://ngisiyuk.dagoo.id/$gambar").into(binding.listProdukGambar)
                }
                binding.namaProduk.text = provider


            }
        }



    }

    override fun getItemCount(): Int {
        return subLayanan.size
    }

}
