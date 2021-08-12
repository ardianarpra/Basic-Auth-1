package com.sst.ngisiyuk.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    lateinit var binding:FragmentWelcomeBinding
    lateinit var navController: NavController

    @Inject lateinit var userPrefs : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val isOpened = userPrefs.getBoolean("isOpened", false)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        if (isOpened) navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToHomeTabContainerFragment())

        return binding.root
    }


}