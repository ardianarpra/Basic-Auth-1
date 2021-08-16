package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sst.ngisiyuk.adapters.SubProdukAdapter
import com.sst.ngisiyuk.databinding.FragmentDataBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class DataFragment : Fragment() {

    lateinit var binding : FragmentDataBinding
    private val layananVM : LayananViewModel by viewModels()
    private lateinit var inputNomor : EditText
    @Inject lateinit var id:String
    @Inject @Named("pin")lateinit var pin:String
    val metrics = DisplayMetrics()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataBinding.inflate(inflater, container, false)
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val tipe = "Paket Data"
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

        layananVM.transPembelianResponse.observe(viewLifecycleOwner,{
            println("Ini di pl :$it")
            if (it != null) {
                handlePembelianResponse(it.status)
            }
        })



        return binding.root
    }

    private fun handlePembelianResponse(status :Boolean) {
        if (status) {
            val successDialog = AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.SUCCESS)
            successDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                lifecycleScope.launch {
                    successDialog.dismiss()
                }
            }, 2000)
        } else {
            val failDialog = AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
            failDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                lifecycleScope.launch {
                    failDialog.dismiss()
                }
            }, 2000)
        }
    }

    private fun initSubProdukRV(data: List<DataXXX>) {
        val adapter = SubProdukAdapter(
            data,
            id,
            layananVM,
            metrics.widthPixels,
            binding.inputNomor,
            pin
        )
        binding.pulsaRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }

}