package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject


class SubOfSubProdukFragmentAdapter(val it: List<DataXXX>, val layananViewModel: LayananViewModel, val id:String): RecyclerView.Adapter<SubOfSubProdukFragmentAdapter.SubOfSubProdukFragmentViewHolder>() {


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
                binding.root.setOnClickListener {
                    println(id)
                    layananViewModel.createTransPPOB(id, id_keuntungan, "085211451587")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return it.size
    }

}
