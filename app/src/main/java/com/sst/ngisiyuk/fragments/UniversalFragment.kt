package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sst.ngisiyuk.databinding.FragmentPLNPascaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UniversalFragment : Fragment() {
    lateinit var binding: FragmentPLNPascaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPLNPascaBinding.inflate(inflater,container, false)


        return binding.root
    }

}