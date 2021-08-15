package com.sst.ngisiyuk.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    lateinit var binding:FragmentWelcomeBinding
    lateinit var navController: NavController
    var isOpened by Delegates.notNull<Boolean>()
    @Inject lateinit var userPrefs : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        isOpened = userPrefs.getBoolean("isOpened", false)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController



        handleDelayedAction()
        return binding.root
    }

    private fun handleDelayedAction() {
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launchWhenResumed {
                if (isOpened) findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToHomeTabContainerFragment())
                else findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSplashFragment())
            }

        }, 3000)
    }


}