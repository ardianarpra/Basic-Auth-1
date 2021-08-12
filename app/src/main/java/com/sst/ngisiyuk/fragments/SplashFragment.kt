package com.sst.ngisiyuk.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.SliderPagerAdapter
import com.sst.ngisiyuk.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding
    private lateinit var auth : FirebaseAuth
    @Inject lateinit var userPrefs : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container, false)
        auth = FirebaseAuth.getInstance()
//        if (auth.currentUser == null){
//            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
//        } else findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())

        val viewPager = binding.photosViewpager.apply {
            isUserInputEnabled = false
        }
        val tabLayout = binding.tabLayout.apply {

        }
        viewPager.adapter = SliderPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

        }.attach()
        binding.nextButton.setOnClickListener {
            if (viewPager.currentItem == 3) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeTabContainerFragment())
                userPrefs.edit().putString("isOpened", "true").apply()
            }
            else viewPager.currentItem = viewPager.currentItem + 1
        }



        return binding.root
    }


}