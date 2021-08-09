package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentPulsaBinding
import com.sst.ngisiyuk.databinding.FragmentPulsaDataBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel


class PulsaFragment : Fragment() {

    lateinit var binding : FragmentPulsaBinding
    private val layananVM : LayananViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPulsaBinding.inflate(inflater, container, false)
        val tipe = "Pulsa"
        layananVM.getIsiLayanan(tipe)

        layananVM.isiLayanan.observe(viewLifecycleOwner,{

        })

        binding.inputNomor.doAfterTextChanged {
            if (it?.length == 4) layananVM.filterNumberAndGetLayanan(it)
        }



        return binding.root
    }

}