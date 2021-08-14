package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sst.ngisiyuk.adapters.PlnVPAdapter
import com.sst.ngisiyuk.databinding.FragmentPLNGroupBinding


class PLNGroupFragment : Fragment() {

    lateinit var binding : FragmentPLNGroupBinding
    private val args : PLNGroupFragmentArgs by navArgs()
    lateinit var tabLayout : TabLayout
    lateinit var viewPager :ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding = FragmentPLNGroupBinding.inflate(layoutInflater, container, false)


        val adapter = PlnVPAdapter(requireParentFragment(), requireContext(), args.tipe)
        tabLayout = binding.tabLayoutPlnGroup
        viewPager = binding.plnViewPager
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

        when(args.tipe){
            "PLN" -> selectPage(0)
            "TOKEN" -> selectPage(1)
        }


        return binding.root
    }

    private fun selectPage(pageIndex: Int) {
        tabLayout.setScrollPosition(pageIndex, 0f, true)
        viewPager.setCurrentItem(pageIndex, false)
    }


}