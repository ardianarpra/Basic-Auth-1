package com.sst.ngisiyuk.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SubProdukAdapter(
    val data: List<DataXXX>,
    val id: String,
    val layananVM: LayananViewModel,
    val widthPixels: Int,
    val inputNomor: TextInputEditText,
    val pin: String,


    ):RecyclerView.Adapter<SubProdukAdapter.SubProdukViewHolder>() {
    lateinit var dialog :AlertDialog.Builder
    lateinit var pinBinding : InputPinLayoutBinding
    lateinit var dialogPin : AlertDialog
    lateinit var shake : Animation

    class SubProdukViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubProdukViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SubProdukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubProdukViewHolder, position: Int) {
        dialog = AlertDialog.Builder(holder.binding.root.context)
        dialogPin = dialog.create()
        pinBinding = InputPinLayoutBinding.inflate(LayoutInflater.from(holder.binding.root.context))





        with(holder){
            with(data[position]){
                Picasso.get().load("https://admin.ngisiyuk.com/$gambar").into(binding.listProdukGambar)
                binding.namaProduk.text = this.nama
                shake = AnimationUtils.loadAnimation(binding.root.context, R.anim.shake).apply {
                    setAnimationListener(object : Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            handleAnimationEnd()
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                    })
                }

                binding.root.setOnClickListener {
                    popUpDialogPin(this)
                }
            }
        }
    }



    private fun popUpDialogPin(data: DataXXX) {
        handlePinDialog(data.id_keuntungan)
        dialogPin.apply {
            setView(pinBinding.root)
            show()
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogPin.window?.attributes)
        lp.width = (widthPixels / 1.5).toInt()
        dialogPin.window?.attributes = lp
        dialogPin.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


    }

    private fun handlePinDialog(idKeuntungan: String) {
        pinBinding.pinInput.doAfterTextChanged {
            if (id != "" && it?.length == 6 && it.toString() == pin) {
                layananVM.createTransPembelian(id, idKeuntungan, inputNomor.text.toString())
                pinBinding.pinInput.text = null
                dialogPin.dismiss()
            }
            else if (it?.length == 6 && it.toString() != pin) {
                pinBinding.pinInput.startAnimation(shake)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size

    }

    private fun handleAnimationEnd() {
        pinBinding.pinInput.text = null
    }

}
