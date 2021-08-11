package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX

class SubOfSubProdukFragmentAdapter(val it: List<DataXXX>): RecyclerView.Adapter<SubOfSubProdukFragmentAdapter.SubOfSubProdukFragmentViewHolder>() {
    class SubOfSubProdukFragmentViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubOfSubProdukFragmentViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SubOfSubProdukFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubOfSubProdukFragmentViewHolder, position: Int) {
        with (holder){
            with(it[position]){
                binding.namaProduk.text = nama
            }
        }
    }

    override fun getItemCount(): Int {
        return it.size
    }

}
