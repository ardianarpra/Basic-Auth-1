package com.sst.ngisiyuk.viewmodels

import android.nfc.Tag
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging

class FCMVIewModel : ViewModel(){

    val token = MutableLiveData<String>()


    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG, "Failed To Get The Token")
            }
            val token = task.result
            Log.d(TAG, "Token : $token")
            this.token.value = token
        }.addOnFailureListener { e ->
            Log.e(
                TAG,
                "Failed To Get The Token : " + e.localizedMessage
            )
        }
    }

    companion object{
        val TAG = "FCM"
    }
}