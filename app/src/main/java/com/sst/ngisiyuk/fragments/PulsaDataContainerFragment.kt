package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.PulsaDataAdapter
import com.sst.ngisiyuk.databinding.FragmentPulsaDataBinding


class PulsaDataContainerFragment : Fragment() {

    lateinit var binding: FragmentPulsaDataBinding
    private val args  by navArgs<PulsaDataContainerFragmentArgs>()
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPulsaDataBinding.inflate(inflater, container, false)
        val adapter = PulsaDataAdapter(requireParentFragment(), requireContext())
        tabLayout = binding.tabPulsa
        viewPager = binding.viewPager

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

        when(args.tipe){
            "Pulsa" -> selectPage(0)
            "Paket Data" -> selectPage(1)
        }



        return binding.root
    }

    private fun selectPage(pageIndex: Int) {
        tabLayout.setScrollPosition(pageIndex, 0f, true)
        viewPager.setCurrentItem(pageIndex, false)
    }


}