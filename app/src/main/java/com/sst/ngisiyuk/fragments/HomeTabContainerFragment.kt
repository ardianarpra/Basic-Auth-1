package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentHomeTabContainerBinding
import me.ibrahimsn.lib.SmoothBottomBar


class HomeTabContainerFragment : Fragment() {

    lateinit var binding: FragmentHomeTabContainerBinding
    lateinit var navController : NavController
    lateinit var homeNavigation : SmoothBottomBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTabContainerBinding.inflate(inflater, container, false)
        val navHost = childFragmentManager.findFragmentById(binding.fragmentContainerView2.id) as NavHostFragment
        navController = navHost.navController
        homeNavigation = binding.bottomBar




        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav, menu)
        binding.bottomBar.setupWithNavController(menu, navController)
        super.onCreateOptionsMenu(menu, inflater)
    }



}