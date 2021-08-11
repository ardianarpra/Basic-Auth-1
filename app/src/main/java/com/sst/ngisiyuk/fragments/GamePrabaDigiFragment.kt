package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.sst.ngisiyuk.adapters.GaPraDiAdapter
import com.sst.ngisiyuk.databinding.FragmentGamePrabaDigiBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel

class GamePrabaDigiFragment : Fragment() {
    lateinit var binding :FragmentGamePrabaDigiBinding
    private val args : GamePrabaDigiFragmentArgs by navArgs()
    private val layananVM :LayananViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamePrabaDigiBinding.inflate(inflater, container, false)
        val bundle = Bundle()
        val subProdukFragment = SubProdukOfGaPraDigiFragment()
        bundle.putString("tipe", args.tipe)
        subProdukFragment.arguments = bundle
        layananVM.nullifySubProduk()
        layananVM.subProduct.observe(viewLifecycleOwner,{
            it?.data?.let {
                binding.gpdViewPager.currentItem = 1
            }
        })


        val adapter = GaPraDiAdapter(requireParentFragment(), subProdukFragment)
        initViewPager(adapter)
        layananVM.getIsiLayanan(args.tipe)

        requireActivity().onBackPressedDispatcher.addCallback {
            if (binding.gpdViewPager.currentItem == 1){
                binding.gpdViewPager.currentItem = 0
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }

        return binding.root
    }



    private fun initViewPager(adapter: GaPraDiAdapter) {
        binding.gpdViewPager.apply {
            this.adapter = adapter
            isUserInputEnabled = false
        }


    }



}