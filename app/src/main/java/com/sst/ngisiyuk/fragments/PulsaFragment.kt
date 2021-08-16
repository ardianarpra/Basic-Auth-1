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
import com.sst.ngisiyuk.databinding.FragmentPulsaBinding
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
class PulsaFragment : Fragment() {

    lateinit var binding : FragmentPulsaBinding
    private val layananVM : LayananViewModel by viewModels()
    lateinit var inputNomor :EditText
    val metrics = DisplayMetrics()


    @Inject lateinit var id :String
    @Inject @Named("pin")lateinit var pin :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPulsaBinding.inflate(inflater, container, false)
        val tipe = "Pulsa"
        layananVM.getIsiLayanan(tipe)
        inputNomor = binding.inputNomor
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)

        layananVM.subProduct.observe(viewLifecycleOwner,{
            it?.data?.let {
                initSubProdukRV(it)
            }
        })

        layananVM.transPembelianResponse.observe(viewLifecycleOwner,{
            println("Ini di pl :$it")
            if (it != null) {
                handlePembelianResponse(it.status)
            }
        })




        handleInputNomor(tipe)
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

    private fun handleInputNomor(tipe: String) {
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
    }

    private fun initSubProdukRV(data: List<DataXXX>, ) {
        val adapter = SubProdukAdapter(data, id, layananVM, metrics.widthPixels, binding.inputNomor, pin)
        binding.pulsaRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }

}