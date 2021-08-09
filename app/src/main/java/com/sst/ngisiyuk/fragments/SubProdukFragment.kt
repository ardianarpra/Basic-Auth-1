package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sst.ngisiyuk.adapters.SubProdukAdapter
import com.sst.ngisiyuk.adapters.SubProdukAdapter2
import com.sst.ngisiyuk.databinding.FragmentSubProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel

class SubProdukFragment : Fragment() {

    lateinit var binding :FragmentSubProdukBinding
    private val layananVM: LayananViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubProdukBinding.inflate(inflater, container, false)
        layananVM.nullifySubLayanan()
        layananVM.isiLayanan.observe(viewLifecycleOwner,{
            it?.data?.let {
                initSubProdukRV(it)
            }
        })


        return binding.root
    }

    private fun initSubProdukRV(data: List<DataXX>) {
        val adapter = SubProdukAdapter2(data)

        binding.subProdukRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }


}