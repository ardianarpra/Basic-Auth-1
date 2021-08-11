package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel

class SubProdukGaPraDigiAdapter(val data: List<DataXX>, val tipe: String, val layananVM: LayananViewModel): RecyclerView.Adapter<SubProdukGaPraDigiAdapter.SubProduk2VH>() {
    class SubProduk2VH(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubProduk2VH {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SubProduk2VH(binding)
    }

    override fun onBindViewHolder(holder: SubProduk2VH, position: Int) {
        with(holder){
            with(data.get(position)){
                binding.namaProduk.text = this.provider
                binding.root.setOnClickListener {
                    layananVM.getListProduk(tipe, provider)
                    println("$tipe, $provider")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}
