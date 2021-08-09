package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
        val adapter = GaPraDiAdapter(requireParentFragment(), requireContext())
        initViewPager(adapter)
        layananVM.getIsiLayanan(args.tipe)






        return binding.root
    }



    private fun initViewPager(adapter: GaPraDiAdapter) {
        binding.gpdViewPager.apply {
            this.adapter = adapter
            isUserInputEnabled = false
        }


    }

}