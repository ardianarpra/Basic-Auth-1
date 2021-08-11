package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentInquiryBinding
import com.sst.ngisiyuk.models.ngisiyuk.CreateInquiryModel


class InquiryFragment : Fragment() {
    lateinit var binding: FragmentInquiryBinding
    private var args : CreateInquiryModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInquiryBinding.inflate(inflater, container, false)
        arguments?.let {
            args = it.getParcelable("inquiry")
        }

        binding.tvTest.text = args.toString()

        return binding.root
    }

}