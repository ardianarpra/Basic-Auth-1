package com.sst.ngisiyuk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.sst.ngisiyuk.databinding.FragmentPLNPascaBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PLNPascaFragment : Fragment() {
    lateinit var binding: FragmentPLNPascaBinding
    private val args : PLNGroupFragmentArgs by navArgs()
    private val layananVM by activityViewModels<LayananViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPLNPascaBinding.inflate(inflater,container, false)
        layananVM.getIsiLayanan(args.tipe)
        layananVM.isiLayanan.observe(requireActivity(),{
            it?.data?.forEach {
                println(it.provider)
            }
        })

        initViewPager()



        return binding.root
    }

    private fun initViewPager() {

    }

}