package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager

import com.sst.ngisiyuk.adapters.SubServiceAdapter
import com.sst.ngisiyuk.databinding.FragmentShowSubServicesBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowSubServicesFragment : Fragment() {

    lateinit var binding: FragmentShowSubServicesBinding
    private val args : ShowSubServicesFragmentArgs by navArgs()
    private val layananVM : LayananViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShowSubServicesBinding.inflate(inflater,container,false)
        layananVM.getIsiLayanan(args.produk.tipe)
        layananVM.nullifySubLayanan()

        layananVM.isiLayanan.observe(viewLifecycleOwner, {
            if (it != null) {
                initSubServiceRV(it.data)
            }
        })


        return binding.root
    }

    private fun initSubServiceRV(isiLayanan: List<DataXX>) {
        val adapter = SubServiceAdapter(isiLayanan)
        binding.subLayanaRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }


}