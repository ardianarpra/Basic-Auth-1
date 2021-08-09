package com.sst.ngisiyuk.fragments.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.databinding.FragmentOtpBinding
import com.sst.ngisiyuk.util.LoadingBar
import com.sst.ngisiyuk.viewmodels.AuthViewModel


class OtpFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    private val viewModel : AuthViewModel by activityViewModels()
    lateinit var binding: FragmentOtpBinding
    lateinit var loading : LoadingBar



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOtpBinding.inflate(inflater,container,false)
        val v = binding.root
        firebaseAuth= FirebaseAuth.getInstance()
        loading = LoadingBar(requireContext())

        println("SMS CODE : ${viewModel.credential.value?.otpCode}")


        // sign up

        viewModel.credential.observe(viewLifecycleOwner, {creds ->
            viewModel.signUpResponse.observe(viewLifecycleOwner, {
                if (it.status){
                    println("status login true dan kok gak login yha ?")
                    viewModel.verifyPhoneNumberWithCode(creds.otpCode, binding.kodeOtp.text.toString(), requireActivity())
                } else Toast.makeText(requireContext(), "signUp gagal", Toast.LENGTH_SHORT).show()
            })

        })



        binding.kodeOtp.doAfterTextChanged {
            if (it != null) {
                binding.verifOtpButton.isEnabled = it.toString() != "" && it.length >= 6
            }
        }

        viewModel.credential.observe(viewLifecycleOwner, {creds ->

            viewModel.hasilCekNomor.observe(viewLifecycleOwner, {
                if (it?.status == false){
                    binding.verifOtpButton.setOnClickListener {
                        loading.showAlert(false)
                        viewModel.signUp()
                    }
                } else if (it?.status == true){
                    binding.verifOtpButton.text = "LOGIN"
                    binding.verifOtpButton.setOnClickListener {
                        loading.showAlert(false)
                        viewModel.verifyPhoneNumberWithCode(creds.otpCode, binding.kodeOtp.text.toString(), requireActivity())
                    }
                }

            })

        })




        viewModel.isSuccessSignIn.observe(viewLifecycleOwner,{


            if (it == true) {
                findNavController().navigate(OtpFragmentDirections.actionOtpFragmentToHomeFragment())
                loading.closeAlert()
            }
        })

        viewModel.phoneAuthCredential.observe(viewLifecycleOwner,{
            binding.kodeOtp.setText(it.smsCode)

            println("smsCode : ${it.smsCode}")
        })



        setTimer()








        return v
    }




    private fun setTimer() {
        val timer = object: CountDownTimer(60000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.countdownTimer.setOnClickListener {  }
                binding.countdownTimer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.countdownTimer.apply {
                    this.text = "resend"
                    this.setOnClickListener {

                        viewModel.resendVerificationCode("+62${viewModel.phoneNumberLive.value!!.drop(1)}", viewModel.credential.value?.token, requireActivity())
                        start()
                    }
                }

            }

        }

        timer.start()
    }
}