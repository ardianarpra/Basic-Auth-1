package com.sst.ngisiyuk.fragments.inquiry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentIdleInquiryBinding
import com.sst.ngisiyuk.databinding.FragmentInquiryBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel


class IdleInquiryFragment : Fragment() {

    lateinit var binding: FragmentIdleInquiryBinding
    private val layananVM by activityViewModels<LayananViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIdleInquiryBinding.inflate(inflater, container, false)

        layananVM.createInquiryResponse.observe(viewLifecycleOwner,{
            println("Ini di idle inquiry")
            if (!it.status) binding.include.lotieIdle.setAnimation(R.raw.anim_not_found)
        })

        return binding.root
    }


}