package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentNewsBinding
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import java.util.*


class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding

    private val userData by activityViewModels<UserDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        userData.dataUser.observe(viewLifecycleOwner,{
            binding.viewKodeRef.text = it?.data?.referral?.uppercase(Locale.getDefault())
        })

        return binding.root
    }


}