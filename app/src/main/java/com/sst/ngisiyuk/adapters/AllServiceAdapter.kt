package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.fragments.HomeFragmentDirections
import com.sst.ngisiyuk.models.ngisiyuk.Produk
import com.sst.ngisiyuk.viewmodels.LayananViewModel

class AllServiceAdapter(private val produk: List<Produk>): RecyclerView.Adapter<AllServiceAdapter.ProdukViewHolder>() {

    class ProdukViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ProdukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        with(holder){
            with(produk[position]){
                Picasso.get().load("https://ngisiyuk.dagoo.id/$gambar").into(binding.listProdukGambar)
                binding.namaProduk.text = tipe

                binding.root.setOnClickListener {
                    println(tipe)
                    when(tipe){
                        "Pulsa", "Paket Data" -> {it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPulsaDataContainerFragment(tipe))}
                        "Game Online", "Digital Wallet", "Voucher HP Prabayar" -> {it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGamePrabaDigiFragment(tipe))}

                    }

                }
            }
        }



    }

    override fun getItemCount(): Int {
        return produk.size
    }

}
