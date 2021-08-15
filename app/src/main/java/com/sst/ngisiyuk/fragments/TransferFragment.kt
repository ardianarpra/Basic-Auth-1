package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.ListBankAdapter
import com.sst.ngisiyuk.databinding.FragmentTransferBinding
import com.sst.ngisiyuk.viewmodels.TransferViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel

class TransferFragment : Fragment() {
    lateinit var binding : FragmentTransferBinding
    lateinit var listBankRV :RecyclerView
    private val topUpModel by activityViewModels<TransferViewModel>()
    private val userVM by activityViewModels<UserDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferBinding.inflate(inflater, container, false)

        return binding.root

    }



}