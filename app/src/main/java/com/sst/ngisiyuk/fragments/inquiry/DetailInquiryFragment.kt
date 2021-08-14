package com.sst.ngisiyuk.fragments.inquiry

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentDetailInquiryBinding
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class DetailInquiryFragment : Fragment(), ThousandSeparator {
    lateinit var binding : FragmentDetailInquiryBinding
    lateinit var balloon: Balloon
    lateinit var pinBinding :InputPinLayoutBinding

    private val layananVM by activityViewModels<LayananViewModel>()
    private val userVM:UserDataViewModel by activityViewModels()

    @Inject lateinit var id:String
    @Inject @Named("pin") lateinit var pin :String

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInquiryBinding.inflate(inflater, container, false)
        pinBinding = InputPinLayoutBinding.inflate(inflater, container, false)

        layananVM.createInquiryResponse.observe(viewLifecycleOwner, { inquiryModel ->
            inquiryModel?.data?.let{
                binding.totalTagihan.text = "Rp. ${thousandSeparator(it.harga_total.toInt(), ".")}"
                binding.namaProduk.text = it.data.name
                binding.nomorTransaksi.text = it.nama_ppob
                binding.tanggalTransaksi.text = "exp : ${it.data.expired}"
                binding.saldoUser.text = "Saldo : ${thousandSeparator(userVM.dataUser.value?.data?.saldo ?: 0, ".")}"
                binding.status.text = it.data.status
                binding.kolomKeduaIsi.text = it.data.data.fare
                binding.kolomKetigaIsi.text = it.data.data.idpel

                handleBayarButton()
                initBalloon(id, it.kode_inquiry, it.harga_total)
            }

            println(inquiryModel?.data)
        })

        return binding.root
    }

    private fun initBalloon(id: String, kodeInquiry: String, hargaTotal: String) {
        pinBinding.apply {
            pinInput.doAfterTextChanged {
                it?.let {
                    if (it.length == 6 && id != "" && it.toString() == pin){
                        layananVM.createTransPPOB(id, kodeInquiry, hargaTotal)
                        pinAnimation.setAnimation(R.raw.anim_success)
                    }
                }
            }

        }
        balloon = createBalloon(requireContext()){
            setLayout(pinBinding.root)
            setBackgroundColor(Color.WHITE)
            setPaddingHorizontal(20)
        }
    }

    private fun handleBayarButton() {
        binding.bayarButton.setOnClickListener {
            balloon.show(binding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layananVM.nullifyInquiryResponse()
    }


}