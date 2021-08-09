package com.sst.ngisiyuk.models

import com.google.firebase.auth.PhoneAuthProvider

data class CredsModel(
    val otpCode : String,
    val token : PhoneAuthProvider.ForceResendingToken
)