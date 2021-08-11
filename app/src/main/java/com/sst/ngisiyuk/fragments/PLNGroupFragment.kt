package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sst.ngisiyuk.adapters.PlnVPAdapter
import com.sst.ngisiyuk.databinding.FragmentPLNGroupBinding


class PLNGroupFragment : Fragment() {

    lateinit var binding : FragmentPLNGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding = FragmentPLNGroupBinding.inflate(layoutInflater, container, false)
        val adapter = PlnVPAdapter(requireParentFragment(), requireContext())
        val tabLayout = binding.tabLayoutPlnGroup
        val viewPager = binding.plnViewPager
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position)
            {
                0 -> tab.text = "PLN Pascabayar"
                1 -> {
                    tab.text = "PLN Token"
                }
            }
        }.attach()


        return binding.root
    }


}