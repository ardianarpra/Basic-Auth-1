package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container, false)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null){
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
        }


        return binding.root
    }


}