package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sst.ngisiyuk.adapters.SubProdukGaPraDigiAdapter
import com.sst.ngisiyuk.databinding.FragmentSubProdukBinding
import com.sst.ngisiyuk.databinding.ProgressDialogBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel

class SubProdukOfGaPraDigiFragment : Fragment() {

    lateinit var binding :FragmentSubProdukBinding
    private val layananVM: LayananViewModel by activityViewModels()
    lateinit var tipe : String
    lateinit var dialog : AlertDialog.Builder
    lateinit var loaderBinding: ProgressDialogBinding
    lateinit var loadingDialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubProdukBinding.inflate(inflater, container, false)
        tipe = arguments?.getString("tipe").toString()
        layananVM.nullifySubLayanan()
        dialog = AlertDialog.Builder(requireContext())
        loaderBinding = ProgressDialogBinding.inflate(inflater, container, false)
        loadingDialog = dialog.apply {
            setView(loaderBinding.root)
        }.create()

        layananVM.isiLayanan.observe(viewLifecycleOwner,{
            it?.data?.let {
                initSubProdukRV(it)
            }
        })

        layananVM.subProduct.observe(viewLifecycleOwner,{
            it?.let {
                loadingDialog.dismiss()
            }
        })




        return binding.root
    }

    private fun initSubProdukRV(data: List<DataXX>) {
        val adapter = SubProdukGaPraDigiAdapter(data, tipe, layananVM, loadingDialog)

        binding.subProdukRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }






}