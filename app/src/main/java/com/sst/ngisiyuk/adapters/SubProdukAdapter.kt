package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX

class SubProdukAdapter(val data: List<DataXXX>):RecyclerView.Adapter<SubProdukAdapter.SubProdukViewHolder>() {
    class SubProdukViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubProdukViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SubProdukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubProdukViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                Picasso.get().load("https://ngisiyuk.dagoo.id/$gambar").into(binding.listProdukGambar)
                binding.namaProduk.text = this.nama


                binding.root.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size

    }

}
