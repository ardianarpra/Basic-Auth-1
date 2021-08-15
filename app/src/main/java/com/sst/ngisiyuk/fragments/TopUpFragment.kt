package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.adapters.ListBankAdapter
import com.sst.ngisiyuk.databinding.FragmentTopUpBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXXXXXXXXX
import com.sst.ngisiyuk.viewmodels.TopUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopUpFragment : Fragment() {

    lateinit var binding : FragmentTopUpBinding
    lateinit var listBankRV : RecyclerView
    private val topUpModel by activityViewModels<TopUpViewModel>()

    @Inject lateinit var id : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpBinding.inflate(inflater, container, false)
        topUpModel.nullifyEveryThing()
        binding.expandableHeader.setOnClickListener {
            binding.expandableLayout.isExpanded = !binding.expandableLayout.isExpanded
        }

        topUpModel.topUpResponse.observe(viewLifecycleOwner,{
            println(it)
        })
        topUpModel.listBankPerusahaan.observe(viewLifecycleOwner,{
            initListBankRV(it.data)
        })
        topUpModel.chosenBank.observe(viewLifecycleOwner,{
            it?.let {
                binding.expandableHeader.text = it.nama_bank
                handleTopUp(it)
            }
        })


        initListBank()
        // test
        handleExpanded()
        return binding.root

    }

    private fun handleExpanded() {

        binding.expandedView.setOnClickListener {
            binding.expandedView.collapse()
        }
    }

    private fun handleTopUp(data: DataXXXXXXXXXX) {
        binding.topupButton.setOnClickListener {
            topUpModel.topUpSaldo(id, data.id_bank, "50000")
        }
    }

    private fun initListBank() {
        if (topUpModel.listBankPerusahaan.value == null) topUpModel.getListBank()
    }

    private fun initListBankRV(data: List<DataXXXXXXXXXX>) {
        val adapter = ListBankAdapter(data, topUpModel, binding.expandableLayout)
        listBankRV = binding.listBankRv
        listBankRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAdapter(adapter)
        }
    }


}