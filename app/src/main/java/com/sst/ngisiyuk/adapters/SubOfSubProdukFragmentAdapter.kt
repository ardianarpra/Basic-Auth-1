package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.databinding.ListProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.util.PopUpBaloon
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


class SubOfSubProdukFragmentAdapter(
    val it: List<DataXXX>,
    val layananViewModel: LayananViewModel,
    val id: String,
    val inputTujuan: TextInputEditText,
): RecyclerView.Adapter<SubOfSubProdukFragmentAdapter.SubOfSubProdukFragmentViewHolder>() {


    lateinit var balloon :PopUpBaloon
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
                            layananViewModel.createTransPPOB(id, id_keuntungan, inputTujuan.text.toString())
                            pinInput.text = null
                            closeBalloon()

                        } else if (it?.length == 6 && it.toString() != "123456") {
                            pinInput.startAnimation(anim)


                        }
                    }
                }

                balloon = PopUpBaloon(binding.root.context, pinBinding.root)




                binding.namaProduk.text = nama
                binding.root.setOnClickListener {
                    println(id)
                    if (inputTujuan.text?.isNotEmpty()!!){
                        println("Not Emt")
                        balloon.showBaloon(binding.root.parent)
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
        balloon.baloon.dismiss()
    }

}
