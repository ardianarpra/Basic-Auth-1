package com.sst.ngisiyuk.fragments.inquiry

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.databinding.FragmentDetailInquiryBinding
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.util.DimensionCatcher
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailInquiryFragment() : Fragment(), ThousandSeparator, DimensionCatcher {
    lateinit var binding : FragmentDetailInquiryBinding
    lateinit var pinBinding :InputPinLayoutBinding
    lateinit var popUpBuilder : AlertDialog.Builder
    lateinit var popUpPin :AlertDialog
    var width by Delegates.notNull<Int>()
    var height by Delegates.notNull<Int>()
    lateinit var metrics: DisplayMetrics

    private val layananVM by activityViewModels<LayananViewModel>()
    private val userVM:UserDataViewModel by activityViewModels()

    @Inject lateinit var id:String
    @Inject @Named("pin") lateinit var pin :String
    @Inject lateinit var shake : Animation


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInquiryBinding.inflate(inflater, container, false)
        pinBinding = InputPinLayoutBinding.inflate(inflater, container, false)
        popUpBuilder = AlertDialog.Builder(requireContext())
        metrics = DisplayMetrics()

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


        layananVM.transPPOBResponse.observe(viewLifecycleOwner,{
            handleTransPPOBResponse(it.data)
        })


        setDisplayDimension(metrics, requireActivity())
        return binding.root
    }

    private fun handleTransPPOBResponse(status: Any?) {
        val successAnim = AestheticDialog.Builder(requireActivity(), DialogStyle.TOASTER, DialogType.SUCCESS)
        val failAnim = AestheticDialog.Builder(requireActivity(), DialogStyle.TOASTER, DialogType.ERROR)


        popUpPin.dismiss()
        if (status != null) successAnim.show() else failAnim.show()
    }

    private fun initBalloon(id: String, kodeInquiry: String, hargaTotal: String) {
        pinBinding.apply {
            pinInput.doAfterTextChanged {
                it?.let {
                    if (it.length == 6 && id != "" && it.toString() == pin){
                        layananVM.createTransPPOB(id, kodeInquiry, hargaTotal)
                        this.pinInput.text = null
                    } else if (it.length == 6 && it.toString() != pin) {
                        pinInput.startAnimation(handleShakeAnimListener())
                    }
                }
            }
        }

        popUpPin = popUpBuilder.apply {
            setView(pinBinding.root)
        }.create()


    }

    private fun handleBayarButton() {
        binding.bayarButton.setOnClickListener {
            popUpPin.show()
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(popUpPin.window?.attributes)
            lp.width = (width / 1.5).toInt()
            popUpPin.window?.attributes = lp
            popUpPin.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun handleShakeAnimListener() : Animation {
        shake.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                pinBinding.pinInput.setText("")
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        return shake
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layananVM.nullifyInquiryResponse()
    }

    override fun setDisplayDimension(metrics: DisplayMetrics, activity: Activity) {
        super.setDisplayDimension(metrics, activity)
        height = metrics.heightPixels
        width = metrics.widthPixels
        println("$height, $width")

    }


}