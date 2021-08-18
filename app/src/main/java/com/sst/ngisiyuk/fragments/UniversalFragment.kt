package com.sst.ngisiyuk.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.SpinnerAnimation
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentUniversalBinding
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.databinding.ProgressDialogBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX
import com.sst.ngisiyuk.models.ngisiyuk.IsiLayananModel
import com.sst.ngisiyuk.util.PopUpBaloon
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UniversalFragment : Fragment() {
    lateinit var binding : FragmentUniversalBinding
    private val layananVM :LayananViewModel by activityViewModels()
    private val userDataVM : UserDataViewModel by activityViewModels()
    lateinit var pinBaloon: PopUpBaloon
    lateinit var alertBuilder: AlertDialog.Builder
    lateinit var loadingDialog : AlertDialog
    lateinit var tipe: String
    @Inject
    lateinit var userId : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUniversalBinding.inflate(inflater, container, false)
        val pinBinding = InputPinLayoutBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        pinBaloon = PopUpBaloon(requireContext(), pinBinding.root)
        alertBuilder = AlertDialog.Builder(requireContext())
        tipe = arguments?.getString("tipe", "").toString()
        println("tipe: $tipe")
        binding.createInquiryButton.setOnClickListener {
            println("Init Pertama")
        }
        pinBinding.pinInput.doAfterTextChanged {
            println(it)
        }
        if (tipe == "null"){
            layananVM.getIsiLayanan("TOKEN")
        } else layananVM.getIsiLayanan(tipe)
        layananVM.isiLayanan.observe(viewLifecycleOwner,{ isiLayananModel ->
            initPowerSpinner(isiLayananModel, tipe)
        })


        layananVM.createInquiryResponse.observe(viewLifecycleOwner,{
            it?.let {
                if (it.status){
                    findNavController().apply {
                    loadingDialog.dismiss()
                    println("Destination" + findNavController().currentDestination?.id +" = "+ R.id.PLNGroupFragment)
//                  TODO("Ada bug di navigasi pln container")
                    navigate(PLNGroupFragmentDirections.actionPLNGroupFragmentToDetailInquiryFragment())
                    }
                } else {

                }
            }
        })

        setLoadingDialog()
        handleOnBackPressed()
        return binding.root
    }

    private fun setLoadingDialog() {
        val loadingBinding = ProgressDialogBinding.inflate(LayoutInflater.from(requireContext()))
        loadingDialog = alertBuilder.setView(loadingBinding.root).create()
    }



    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback{
            if (binding.powerSpinner.isShowing) {
                binding.powerSpinner.dismiss()
                requireActivity().onBackPressed()
            } else  {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    private fun initPowerSpinner(isiLayananModel: IsiLayananModel?, tipe: String?) {

        val arrayProvider = mutableListOf<String>()

        isiLayananModel?.data?.let { isiLayananData ->
            isiLayananData.forEach {
                arrayProvider.add(it.provider)
            }
        }

        binding.powerSpinner.apply {
            setItems(arrayProvider)
            spinnerPopupAnimation = SpinnerAnimation.FADE
            spinnerPopupBackgroundColor = Color.WHITE
            hint = "Pilih ${if (tipe == "null") "Token" else tipe}"
            setOnSpinnerOutsideTouchListener { view, motionEvent ->
                binding.powerSpinner.dismiss()
            }
            setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                val chosenSpinner = isiLayananModel?.data?.find {
                    it.provider == newItem
                }

                handleClickToCreateInquiry(chosenSpinner)

            })





        }


    }

    private fun handleClickToCreateInquiry(chosenSpinner: DataXX?) {

        binding.createInquiryButton.setOnClickListener { view ->
            if (chosenSpinner != null && binding.nomorTujuanPln.text?.isNotBlank()!! && userId != ""){
                println("Benar")
                loadingDialog.show()
                layananVM.createInquiry(userId, chosenSpinner.kode, binding.nomorTujuanPln.text.toString() )
            } else if (userId == "" && tipe == "TOKEN"){
                findNavController().navigate(PulsaDataContainerFragmentDirections.actionPulsaDataContainerFragmentToDetailInquiryFragment())
            }

        }
    }

    override fun onPause() {
        super.onPause()

        binding.powerSpinner.dismiss()
    }
}