package com.sst.ngisiyuk.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerView
import com.skydoves.powerspinner.SpinnerAnimation
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentPLNTokenBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXX
import com.sst.ngisiyuk.models.ngisiyuk.IsiLayananModel
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PLNTokenFragment : Fragment() {
    lateinit var binding : FragmentPLNTokenBinding
    private val layananVM :LayananViewModel by viewModels()
    private val userDataVM : UserDataViewModel by activityViewModels()
    @Inject
    lateinit var id : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPLNTokenBinding.inflate(inflater, container, false)

        val args = arguments?.getString("tipe", "")
        println("args: $args")

        if (args == null){
            layananVM.getIsiLayanan("TOKEN")
        } else layananVM.getIsiLayanan("PDAM")
        layananVM.isiLayanan.observe(viewLifecycleOwner,{ isiLayananModel ->

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


            binding.isiLayananButton.setOnClickListener { view ->
                if (chosenSpinner != null && binding.nomorTujuanPln.text?.isNotBlank()!!){
                    println("Not null nor empty")
                    layananVM.createInquiry(id, chosenSpinner?.kode!!, binding.nomorTujuanPln.text.toString() )
                }
            }




        })


        layananVM.createInquiryResponse.observe(viewLifecycleOwner,{
            println(it.data)
        })




        return binding.root
    }
}