package com.sst.ngisiyuk.fragments.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentSignInBinding
import com.sst.ngisiyuk.util.LoadingBar
import com.sst.ngisiyuk.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    lateinit var binding : FragmentSignInBinding
    private val authViewModel : AuthViewModel by activityViewModels()
    lateinit var loading : LoadingBar

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        loading = LoadingBar(requireContext())

        binding.buttonLogin.setOnClickListener {


            if (binding.nomorLoginUser.text.toString().isNotBlank()) {
                loading.showAlert(false)
                authViewModel.updateNumber(binding.nomorLoginUser.text.toString())
                authViewModel.cekPenyediaJasa(binding.nomorLoginUser.text.toString())
            }else{
                Toast.makeText(requireContext(), "Nomor HP tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }



        authViewModel.hasilCekNomor.observe(viewLifecycleOwner, {
            authViewModel.resetIsVerificationFail()
            if (it?.data == 0){
                loading.closeAlert()
                binding.loginNotif.text = "*nomor anda nelum terdaftar, silahkan login"
                Toast.makeText(requireContext(), "Nomor belum terdaftar", Toast.LENGTH_SHORT).show()
            } else if (it?.data == 1) authViewModel.startPhoneNumberVerification(binding.nomorLoginUser.text.toString(), requireActivity())
        })

        authViewModel.otpSent.observe(viewLifecycleOwner, {
            if(it == true){
                loading.closeAlert()
                findNavController().navigate(R.id.action_signInFragment_to_otpFragment)
            }else loading.closeAlert()
        })

        authViewModel.isVerificationFail.observe(viewLifecycleOwner,{
            if (it == true){
                loading.closeAlert()
                binding.loginNotif.text = "Mohon Periksa Lagi Nomor Anda"
            } else binding.loginNotif.text = null
        })

        binding.signUpText.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }



        return binding.root
    }


}