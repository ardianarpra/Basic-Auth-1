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
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel


class SubOfSubProdukFragmentAdapter(
    val it: List<DataXXX>,
    val layananViewModel: LayananViewModel,
    val id: String,
    val inputTujuan: TextInputEditText,
    val widthPixels: Int,
): RecyclerView.Adapter<SubOfSubProdukFragmentAdapter.SubOfSubProdukFragmentViewHolder>() {

    lateinit var dialog :AlertDialog.Builder
    lateinit var inputPinDialog :AlertDialog
    lateinit var pinBinding : InputPinLayoutBinding

    class SubOfSubProdukFragmentViewHolder(val binding: ListProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubOfSubProdukFragmentViewHolder {
        val binding = ListProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SubOfSubProdukFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubOfSubProdukFragmentViewHolder, position: Int) {
        dialog = AlertDialog.Builder(holder.binding.root.context)
        inputPinDialog = dialog.create()
        with (holder){
            with(it[position]){
                val anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.shake).apply {
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
                pinBinding = InputPinLayoutBinding.inflate(LayoutInflater.from(binding.root.context)).apply {
                    pinInput.doAfterTextChanged {
                        if (it?.length == 6 && it.toString() == "123456"){
                            layananViewModel.createTransPembelian(id, id_keuntungan, inputTujuan.text.toString())
                            pinInput.text = null
                            closeBalloon()

                        } else if (it?.length == 6 && it.toString() != "123456") {
                            pinInput.startAnimation(anim)


                        }
                    }
                }






                binding.namaProduk.text = nama
                binding.root.setOnClickListener {
                    inputPinDialog.setView(pinBinding.root)
                    println(id)
                    if (inputTujuan.text?.isNotEmpty()!!){
                        println("Not Emt")
                        inputPinDialog.show()
                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(inputPinDialog.window?.attributes)
                        lp.width = (widthPixels / 1.5).toInt()
                        inputPinDialog.window?.attributes = lp
                        inputPinDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    }
                }

            }
        }
    }

    private fun handleAnimationEnd() {
        pinBinding.pinInput.text = null
    }

    override fun getItemCount(): Int {
        return it.size
    }

    private fun closeBalloon(){
        inputPinDialog.dismiss()
    }

}
