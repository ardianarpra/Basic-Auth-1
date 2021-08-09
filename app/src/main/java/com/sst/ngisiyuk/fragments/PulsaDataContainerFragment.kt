package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.PulsaDataAdapter
import com.sst.ngisiyuk.databinding.FragmentPulsaDataBinding


class PulsaDataContainerFragment : Fragment() {

    lateinit var binding: FragmentPulsaDataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPulsaDataBinding.inflate(inflater, container, false)
        val adapter = PulsaDataAdapter(requireParentFragment(), requireContext())
        val tabLayout = binding.tabPulsa
        val viewPager = binding.viewPager

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position)
            {
                0 -> tab.text = "Pulsa"
                1 -> {
                    tab.text = "Paket Data"
                }
            }
        }.attach()

        return binding.root
    }


}