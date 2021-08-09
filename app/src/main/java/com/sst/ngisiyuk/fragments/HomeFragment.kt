package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sst.ngisiyuk.adapters.AllServiceAdapter
import com.sst.ngisiyuk.databinding.FragmentHomeBinding
import com.sst.ngisiyuk.models.ngisiyuk.Produk
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator


class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private val layananViewModel : LayananViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        layananViewModel.allServices.observe(viewLifecycleOwner,{
            initServiceRV(it)
        })

        return binding.root
    }

    private fun initServiceRV(services: ArrayList<Produk>) {
        val adapter = AllServiceAdapter(services)
        binding.serviceRv.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.HORIZONTAL, false)
            setAdapter(adapter)
            itemAnimator = SlideInDownAnimator()
        }



    }


}