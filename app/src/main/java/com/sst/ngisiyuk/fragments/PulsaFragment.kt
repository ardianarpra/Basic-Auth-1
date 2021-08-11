package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sst.ngisiyuk.adapters.SubProdukAdapter
import com.sst.ngisiyuk.databinding.FragmentPulsaBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PulsaFragment : Fragment() {

    lateinit var binding : FragmentPulsaBinding
    private val layananVM : LayananViewModel by viewModels()
    lateinit var inputNomor :EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPulsaBinding.inflate(inflater, container, false)
        val tipe = "Pulsa"
        layananVM.getIsiLayanan(tipe)
        inputNomor = binding.inputNomor
        layananVM.subProduct.observe(viewLifecycleOwner,{
            it?.data?.let {
                initSubProdukRV(it)
            }
        })



        inputNomor.apply {
            doOnTextChanged { text, start, before, count ->
                println("text :$text,  start:$start, count:$count,  before:$before")
                if (text?.length == 4 && count == 1){
                    layananVM.getPulsaSubProduk(tipe,this.text.toString())
                } else if (text?.length!! > 10 && count > 10) {
                    layananVM.getPulsaSubProduk(tipe,this.text.toString().take(4))
                }
            }
        }



        return binding.root
    }

    private fun initSubProdukRV(data: List<DataXXX>) {
        val adapter = SubProdukAdapter(data)
        binding.pulsaRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }

}