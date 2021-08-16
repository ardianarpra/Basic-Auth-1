package com.sst.ngisiyuk.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.adapters.ListBankAdapter
import com.sst.ngisiyuk.databinding.DetailTopUpLayoutBinding
import com.sst.ngisiyuk.databinding.FragmentTopUpBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXXXXXXXXX
import com.sst.ngisiyuk.models.ngisiyuk.TopUpSaldoModel
import com.sst.ngisiyuk.util.HideKeyboard
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.TopUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopUpFragment : Fragment(), HideKeyboard, ThousandSeparator {

    lateinit var binding : FragmentTopUpBinding
    lateinit var listBankRV : RecyclerView
    lateinit var dialog : AlertDialog.Builder
    lateinit var detailDialog : AlertDialog
    lateinit var detailDialogBinding: DetailTopUpLayoutBinding

    private val topUpModel by activityViewModels<TopUpViewModel>()

    @Inject lateinit var id : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpBinding.inflate(inflater, container, false)
        dialog = AlertDialog.Builder(requireContext())
        detailDialog = dialog.create()
        detailDialogBinding = DetailTopUpLayoutBinding.inflate(inflater, container, false)
        topUpModel.nullifyEveryThing()
        binding.expandableHeader.setOnClickListener {
            binding.expandableLayout.isExpanded = !binding.expandableLayout.isExpanded
        }

        topUpModel.topUpResponse.observe(viewLifecycleOwner,{
            println(it)
            binding.inputJumlahSaldo.text = null
            it?.let {
                initDialogDetail(it)
            }

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
        handleTextInput()
        handleBackButton()
        // test

        return binding.root

    }

    private fun handleBackButton() {
        binding.topUpBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDialogDetail(data: TopUpSaldoModel) {
        detailDialogBinding.apply {
            this.detail.text = data.pesan
        }
        detailDialog.setView(detailDialogBinding.root)
        detailDialog.show()
    }




    private fun handleTextInput() {

        binding.inputJumlahSaldo.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v, requireActivity())
        }
    }


    private fun handleTopUp(data: DataXXXXXXXXXX) {
        binding.topupButton.setOnClickListener {
            if (binding.inputJumlahSaldo.text?.isNotEmpty() == true) {
                topUpModel.topUpSaldo(id, data.id_bank, binding.inputJumlahSaldo.getNumericValue().toString())
            }
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