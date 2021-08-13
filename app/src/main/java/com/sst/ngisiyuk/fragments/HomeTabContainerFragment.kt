package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentHomeTabContainerBinding


class HomeTabContainerFragment : Fragment() {

    lateinit var binding: FragmentHomeTabContainerBinding
    lateinit var navController : NavController
    lateinit var homeNavigation : BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTabContainerBinding.inflate(inflater, container, false)
        val navHost = childFragmentManager.findFragmentById(binding.fragmentContainerView2.id) as NavHostFragment
        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav)

        graph.startDestination = R.id.homeFragment
        navController = navHost.navController
        homeNavigation = binding.bottomNav
        navHost.navController.graph = graph

        NavigationUI.setupWithNavController(homeNavigation, navController)


        return binding.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.bottom_nav, menu)
//        binding.bottomBar.setupWithNavController(menu, navController)
//        super.onCreateOptionsMenu(menu, inflater)
//    }



}