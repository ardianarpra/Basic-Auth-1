package com.sst.ngisiyuk.fragments

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sst.ngisiyuk.databinding.FragmentAkunBinding
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.os.Build
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.skydoves.balloon.createBalloon
import javax.inject.Inject


@AndroidEntryPoint
class AkunFragment : Fragment(), ThousandSeparator {
    lateinit var binding: FragmentAkunBinding

    private val userVM by activityViewModels<UserDataViewModel>()

    @Inject lateinit var auth :FirebaseAuth
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        handleBackButton()
        handleEditButton()
        handleTopUpButton()
        handleTransferButton()
        handleCopyKode()

        userVM.dataUser.observe(viewLifecycleOwner,{ profil ->

            binding.akunNama.text = profil?.data?.nama_pelanggan ?: ""
            binding.akunNomorHp.text = profil?.data?.no_hp ?: ""
            binding.kodeReferral.text = profil?.data?.referral ?: ""
            binding.included.nominalSaldoUser.text = "Rp. ${thousandSeparator((profil?.data?.saldo ?: 0), "")}"
            binding.logoutButton.apply {
                setOnClickListener {
                    if (profil != null) auth.signOut() else findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToAuthFragment())
                }

                text = if (profil == null) "Log In" else "Log Out"
            }


        })



        return binding.root
    }



    private fun handleCopyKode() {
        val baloon = createBalloon(requireContext()){
            text = "Kode Dicopy"
            autoDismissDuration = 1000
            backgroundColor = Color.parseColor("#BED8E9")
            textColor = Color.BLACK
            textSize = 15f
            setPaddingHorizontal(20)
            setPaddingVertical(10)
            elevation = 5f
            marginBottom = 10
        }

        binding.kodeReferral.setOnClickListener {
            val clipboardManager = requireActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData
                .newPlainText(binding.kodeReferral.text, binding.kodeReferral.text)
            clipboardManager.setPrimaryClip(clipData)

            baloon.show(binding.kodeReferral)
        }


    }

    private fun handleTransferButton() {
        binding.included.transfer.setOnClickListener {
            findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToTransferFragment())
        }
    }

    private fun handleTopUpButton() {
        binding.included.topUpUser.setOnClickListener {
            findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToTopUpFragment())
        }
    }

    private fun handleEditButton() {
//        TODO("Not yet implemented")
    }

    private fun handleBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}