package com.sst.ngisiyuk.fragments.inquiry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentDetailInquiryBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInquiryFragment : Fragment() {
    lateinit var binding : FragmentDetailInquiryBinding
    private val layananVM by activityViewModels<LayananViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInquiryBinding.inflate(inflater, container, false)

        layananVM.createInquiryResponse.observe(viewLifecycleOwner, {
            binding.test.text = it.data.data.toString()
        })

        return binding.root
    }


}