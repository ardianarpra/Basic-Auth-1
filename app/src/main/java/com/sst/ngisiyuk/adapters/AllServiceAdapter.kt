package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.databinding.PopupLoginBinding
import com.sst.ngisiyuk.fragments.HomeFragmentDirections
import com.sst.ngisiyuk.models.ngisiyuk.Produk

class AllServiceAdapter(private val produk: List<Produk>): RecyclerView.Adapter<AllServiceAdapter.ProdukViewHolder>() {

    class ProdukViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)
    lateinit var loginPopUp : AlertDialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ProdukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val loginBinding = PopupLoginBinding.inflate(LayoutInflater.from(holder.binding.root.context)).apply {
            popupButton.setOnClickListener {
                holder.binding.root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthFragment())
                loginPopUp.dismiss()
            }
        }
        loginPopUp = AlertDialog.Builder(holder.binding.root.context).apply {
            this.setView(loginBinding.root)
        }.create()

        with(holder){
            with(produk[position]){
                Picasso.get().load("https://admin.ngisiyuk.com/$gambar").into(binding.listProdukGambar)
                binding.namaProduk.text = tipe

                binding.root.setOnClickListener {

                    if (FirebaseAuth.getInstance().currentUser == null){
                        loginPopUp.show()
                        return@setOnClickListener
                    }

                    println(tipe)
                    when(tipe){
                        "Pulsa", "Paket Data" -> {it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPulsaDataContainerFragment(tipe))}
                        "Game Online", "Digital Wallet", "Voucher HP Prabayar" -> {it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGamePrabaDigiFragment(tipe))}
                        "PLN", "TOKEN" -> {it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPLNGroupFragment(tipe))}
                        else -> it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUniversalFragment(tipe))
                    }

                }
            }
        }



    }

    override fun getItemCount(): Int {
        return produk.size
    }

}
