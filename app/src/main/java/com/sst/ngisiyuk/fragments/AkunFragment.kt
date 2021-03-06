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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sst.ngisiyuk.databinding.FragmentAkunBinding
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.Constant
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.MenuAkunAdapter
import com.sst.ngisiyuk.databinding.PopupLoginBinding
import javax.inject.Inject


@AndroidEntryPoint
class AkunFragment : Fragment(), ThousandSeparator {
    lateinit var binding: FragmentAkunBinding

    private val userVM by activityViewModels<UserDataViewModel>()
    lateinit var loginPopUp : AlertDialog

    @Inject lateinit var auth :FirebaseAuth
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        handleBackButton()
        handleEditButton()
        handleCopyKode()
        handleIconLoginTransformation()
        initMenuAkunRV()
        handleNotLogin()

        userVM.dataUser.observe(viewLifecycleOwner,{ profil ->

            binding.akunNama.text = profil?.data?.nama_pelanggan ?: ""
            binding.akunNomorHp.text = if (profil == null) "" else "0${profil.data.no_hp}"
            binding.kodeReferral.text = profil?.data?.referral ?: ""




        })





        return binding.root
    }

    private fun handleNotLogin() {
        if(auth.currentUser == null){
            val loginBinding = PopupLoginBinding.inflate(LayoutInflater.from(requireContext())).apply {
                popupButton.setOnClickListener {
                    findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToAuthFragment())
                    loginPopUp.dismiss()
                }
            }
            loginPopUp = AlertDialog.Builder(requireContext()).apply {
                this.setView(loginBinding.root)
            }.create()

        }
    }

    private fun handleIconLoginTransformation() {
        auth.addAuthStateListener {
            if (it.currentUser == null){
                binding.editButton.setImageResource(R.drawable.ic_login)
            } else binding.editButton.setImageResource(R.drawable.ic_edit)
        }

    }

    private fun initMenuAkunRV() {
        println("Akun RV")
        val adapter = MenuAkunAdapter(Constant.menuAkun)
        binding.akunRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAdapter(adapter)
        }
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





    private fun handleEditButton() {
        auth.addAuthStateListener {firebase ->
            binding.editButton.setOnClickListener {
                if (firebase.currentUser == null) findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToAuthFragment())
                else findNavController().navigate(AkunFragmentDirections.actionAkunFragmentToEditAkunFragment())
            }

        }
    }

    private fun handleBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}