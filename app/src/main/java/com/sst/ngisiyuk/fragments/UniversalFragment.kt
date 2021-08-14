package com.sst.ngisiyuk.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.SpinnerAnimation
import com.sst.ngisiyuk.databinding.FragmentUniversalBinding
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
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
    private val layananVM :LayananViewModel by viewModels()
    private val userDataVM : UserDataViewModel by activityViewModels()
    lateinit var pinBaloon: PopUpBaloon
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
        val tipe = arguments?.getString("tipe", "")
        println(userId)

        pinBinding.pinInput.doAfterTextChanged {
            println(it)
        }
        if (tipe == null){
            layananVM.getIsiLayanan("TOKEN")
        } else layananVM.getIsiLayanan(tipe)
        layananVM.isiLayanan.observe(viewLifecycleOwner,{ isiLayananModel ->
            initPowerSpinner(isiLayananModel, tipe)
        })


        layananVM.createInquiryResponse.observe(viewLifecycleOwner,{
            if (it.status){
                findNavController().navigate(UniversalFragmentDirections.actionUniversalFragmentToDetailInquiryFragment())
            } else {

            }
        })




        return binding.root
    }

    private fun initPowerSpinner(isiLayananModel: IsiLayananModel?, tipe: String?) {

        val arrayProvider = mutableListOf<String>()
        var chosenSpinner : DataXX? = null
        isiLayananModel?.data?.let { isiLayananData ->
            isiLayananData.forEach {
                arrayProvider.add(it.provider)
            }
        }

        binding.powerSpinner.apply {
            setItems(arrayProvider)
            spinnerPopupAnimation = SpinnerAnimation.FADE
            spinnerPopupBackgroundColor = Color.WHITE
            tipe?.let {
                hint = "Pilih $tipe"
            }
            setOnSpinnerOutsideTouchListener { view, motionEvent ->
                binding.powerSpinner.dismiss()
            }
            setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                isiLayananModel?.data?.let {
                    chosenSpinner = isiLayananModel.data.find {
                        it.provider == newItem
                    }
                }

            })


        }

        binding.createInquiryButton.setOnClickListener { view ->
            if (chosenSpinner != null && binding.nomorTujuanPln.text?.isNotBlank()!!){
                layananVM.createInquiry(userId, chosenSpinner?.kode!!, binding.nomorTujuanPln.text.toString() )
            }


        }
    }
}