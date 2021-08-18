package com.sst.ngisiyuk.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentNewsBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.ReferralViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import java.util.*


class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding

    private val userData by activityViewModels<UserDataViewModel>()
    private val referralVM by activityViewModels<ReferralViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        userData.dataUser.observe(viewLifecycleOwner,{
            binding.viewKodeRef.text = it?.data?.referral?.uppercase(Locale.getDefault())
        })

        handleCopyButton()
        handleMasukkanKodeRefs()
        handleBagikanRefs()
        return binding.root
    }

    private fun handleBagikanRefs() {
        binding.bagikanKodeRefs.setOnClickListener {

        }
    }

    private fun handleMasukkanKodeRefs() {
        binding.buttonTambahkanReferral.setOnClickListener {
            referralVM.updateParent(binding.kodeReferalTeman.text.toString())
        }
    }

    private fun handleCopyButton() {

        val baloon = createBalloon(requireActivity()){
            text = "Kode Dicopy"
            autoDismissDuration = 2000
            backgroundColor = Color.parseColor("#BED8E9")
            textColor = Color.BLACK
            textSize = 15f
            setPaddingHorizontal(20)
            setPaddingVertical(10)
            elevation = 5f
            marginBottom = 10
        }

        binding.copyRefs.setOnClickListener {
            val clipboardManager = requireActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData
                .newPlainText(binding.viewKodeRef.text, binding.viewKodeRef.text)
            clipboardManager.setPrimaryClip(clipData)

            baloon.show(binding.copyRefs)
        }

    }


}