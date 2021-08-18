package com.sst.ngisiyuk.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.databinding.PopupSignoutBinding
import com.sst.ngisiyuk.models.ngisiyuk.MenuAkun
import dagger.hilt.android.scopes.ActivityScoped
import android.view.WindowManager
import androidx.navigation.findNavController
import com.sst.ngisiyuk.databinding.ListMenuAkunBinding
import com.sst.ngisiyuk.fragments.AkunFragmentDirections


@ActivityScoped
class MenuAkunAdapter(val menuAkun: ArrayList<MenuAkun>) : RecyclerView.Adapter<MenuAkunAdapter.MenuAkunViewHolder>() {
    val auth = FirebaseAuth.getInstance()
    lateinit var dialog : AlertDialog.Builder
    lateinit var signOutPopUp: AlertDialog
    lateinit var popSignOutBinding : PopupSignoutBinding

    class MenuAkunViewHolder(val binding: ListMenuAkunBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAkunViewHolder {
        val binding = ListMenuAkunBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        popSignOutBinding = PopupSignoutBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            popupButtonSignOut.setOnClickListener {
                handleSignOut()
            }
        }
        dialog = AlertDialog.Builder(parent.context)
        signOutPopUp = dialog.apply {
            setView(popSignOutBinding.root)
        }.create()

        return MenuAkunViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSignOut() {
        auth.signOut()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MenuAkunViewHolder, position: Int) {

        with(holder){
            with(menuAkun[position]){
                binding.listProdukGambar.setImageResource(pathGambar)
                binding.namaProduk.text = namaMenu


                binding.root.setOnClickListener {
                    when(position){
                        2 -> {
                            it.findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToRingkasanAkunFragment())
                        }
                        5 -> {
                            showPopUpSignOut()
                        }

                    }
                }

            }
        }


    }

    private fun showPopUpSignOut() {
        signOutPopUp.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(signOutPopUp.window?.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        signOutPopUp.window?.attributes = lp
        signOutPopUp.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun getItemCount(): Int {
        return if (auth.currentUser == null) 0 else menuAkun.size
    }

}
