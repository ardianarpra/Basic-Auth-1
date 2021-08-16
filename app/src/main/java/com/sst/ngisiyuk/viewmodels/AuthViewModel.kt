package com.sst.ngisiyuk.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.sst.ngisiyuk.models.CredsModel
import com.sst.ngisiyuk.models.ngisiyuk.CekPelanggan
import com.sst.ngisiyuk.models.ngisiyuk.CreateProfil
import com.sst.ngisiyuk.services.NgisiyukServices
import com.sst.ngisiyuk.util.LoadingBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel  @Inject constructor(
    private val api: NgisiyukServices,
    ) : ViewModel() {

    private var firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    val credential = MutableLiveData<CredsModel>()
    val hasilCekNomor = MutableLiveData<CekPelanggan?>()
    val otpSent = MutableLiveData(false)
    val isSuccessSignIn = MutableLiveData<Boolean>()
    val signUpResponse = MutableLiveData<CreateProfil>()
    val isVerificationInComplete = MutableLiveData(false)
    val isVerificationFail = MutableLiveData(false)
    val phoneAuthCredential = MutableLiveData<PhoneAuthCredential>()
    val phoneNumberLive = MutableLiveData<String>()



    // user field for signUp

    private var phoneNumber = ""
    private var namaUser = ""
    private var kotaUser = ""
    private var pin = ""


    fun cekUser(number :String){
        viewModelScope.launch {
            val response = api.cekPelanggan(number.drop(1))
            if (response.isSuccessful) {
                hasilCekNomor.value = response.body()
                println("HasilCEk : ${response.body()} , number:$number")
            }



        }
    }
    fun signUp(referral:String) {
        viewModelScope.launch {
            val response = api.createProfil(phoneNumber, namaUser, kotaUser, pin, referral)
            if (response.isSuccessful) signUpResponse.value = response.body()
        }
    }
    fun fillDataUser(no: String, nama: String, alamat: String, pin:String){
        phoneNumber = no
        namaUser = nama
        kotaUser = alamat
        this.pin = pin
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Log.d("Login", "onVerificationCompleted: LoginSuccess")
            phoneAuthCredential.value = p0
            isVerificationInComplete.value = true


            println("Berhasil OTP")

        }

        override fun onVerificationFailed(p0: FirebaseException) {
            println("Failed Verif")
            isVerificationFail.value = true

        }

        override fun onCodeSent(otpKode: String, token: PhoneAuthProvider.ForceResendingToken) {
            credential.value = CredsModel(otpKode, token)

            otpSent.value = true

            println("Otp Sent")

        }

    }



    fun startPhoneNumberVerification(phoneNumber: String, activity: Activity) {

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+62${phoneNumber.drop(1)}")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, context: Activity) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {

                    isSuccessSignIn.value = true
                    // activity?.finish()

                    println("Login Succeed")

                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                    isSuccessSignIn.value = false
                    println("Login Failed")
                }
            }
    }

    fun verifyPhoneNumberWithCode(verificationId: String?, code: String, activity: Activity) {

        val creds = PhoneAuthProvider.getCredential(verificationId!!, code)

        println("Disini")

         signInWithPhoneAuthCredential(creds, activity)
    }



    // [START resend_verification]
    fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
        activity: Activity
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    // [END resend_verification]





    fun nullifying(){
        hasilCekNomor.value = null
    }

    fun updateNumber (no:String){
        phoneNumberLive.value = no
    }

    fun resetIsVerificationFail(){
        isVerificationFail.value = false
    }
    fun falsifyOtp(){
        otpSent.value = false
    }
    fun falsifyIsSuccessSignIn(){
        isSuccessSignIn.value = false
    }




}