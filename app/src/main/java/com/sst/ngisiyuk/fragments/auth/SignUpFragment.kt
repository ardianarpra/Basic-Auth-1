package com.sst.ngisiyuk.fragments.auth

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.FragmentSignUpBinding
import com.sst.ngisiyuk.util.LoadingBar
import com.sst.ngisiyuk.viewmodels.AuthViewModel

class SignUpFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth

    lateinit var namaUser: EditText
    lateinit var nomorUser: EditText
    lateinit var kotaUser: EditText
    private val viewModel : AuthViewModel by activityViewModels()
    private lateinit var loading : LoadingBar

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val v = binding.root
        loading = LoadingBar(requireContext())


        firebaseAuth = FirebaseAuth.getInstance()
        namaUser = binding.namaUser
        nomorUser = binding.nomorSignUpUser
        kotaUser = binding.kotaUser

        binding.signupPin.apply {
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    binding.signUpNotif.text = "Pin Harus 6 karakter"
                } else binding.signUpNotif.text = null
            }

            doAfterTextChanged {
                binding.signUpNotif.apply {
                    if (it?.length == 6) setTextColor(Color.GREEN)
                    else if(binding.signupPin.text.isEmpty()){
                        binding.signUpNotif.text = null
                    }
                    else {
                        setTextColor(Color.RED)
                        binding.signUpNotif.text = "Pin Harus 6 karakter"
                    }
                }
            }
        }


        binding.buatAkunButton.setOnClickListener {


            if(nomorUser.text.isEmpty() || namaUser.text.isEmpty() || kotaUser.text.isEmpty()){

                Toast.makeText(requireContext(), "Semua data harus lengkap !!", Toast.LENGTH_SHORT).show()

            }else {
                loading.showAlert(false)
                viewModel.cekPenyediaJasa(nomorUser.text.toString())
            }
        }


        viewModel.hasilCekNomor.observe(viewLifecycleOwner, {
            if (it?.data == 0){
                viewModel.fillDataUser(nomorUser.text.toString().drop(1), namaUser.text.toString(), kotaUser.text.toString())
                viewModel.startPhoneNumberVerification(nomorUser.text.toString(), requireActivity())
            } else if (it?.data == 1) {
                loading.closeAlert()
                binding.signUpNotif.text = "*nomor anda sudah terdaftar, silahkan login"
                Toast.makeText(
                    requireContext(),
                    "Nomor Anda Sudah Terdaftar, Silahkan Login",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.otpSent.observe(viewLifecycleOwner, {
            if(it == true){
                loading.closeAlert()
                findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
            }
            else loading.closeAlert()
        })

        viewModel.isVerificationFail.observe(viewLifecycleOwner,{
            if (it == true){
                loading.closeAlert()
                binding.signUpNotif.text = "Mohon periksa kembali nomor yang anda masukkan"
            } else binding.signUpNotif.text = null
        })



        binding.signInText.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }





        return v
    }



}