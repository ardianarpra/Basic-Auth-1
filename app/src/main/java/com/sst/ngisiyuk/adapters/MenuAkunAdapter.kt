package com.sst.ngisiyuk.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.Constant
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.MenuAkun
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MenuAkunAdapter(val menuAkun: ArrayList<MenuAkun>) : RecyclerView.Adapter<MenuAkunAdapter.MenuAkunViewHolder>() {
    val auth = FirebaseAuth.getInstance()


    class MenuAkunViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAkunViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MenuAkunViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MenuAkunViewHolder, position: Int) {
        with(holder){
            with(menuAkun[position]){
                binding.listProdukGambar.setImageResource(pathGambar)
                binding.namaProduk.text = namaMenu

                when(position){
                    6 -> {
                        binding.root.setOnClickListener {
                            auth.signOut()
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return if (auth.currentUser == null) 0 else menuAkun.size
    }

}
