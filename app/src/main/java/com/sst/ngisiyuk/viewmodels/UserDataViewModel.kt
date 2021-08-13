package com.sst.ngisiyuk.viewmodels

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.models.ngisiyuk.GetProfil
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val api : NgisiyukServices,
    private val userPrefs: SharedPreferences,
    private val auth: FirebaseAuth
): ViewModel() {

    val dataUser = MutableLiveData<GetProfil?>()

    init{
        Log.d(TAG, "USerVM: Terinit")
        
        userPrefs.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->
            println(s)
        }

        auth.addAuthStateListener {
            if (it.currentUser == null){
                userPrefs.edit().remove("id").apply()
                userPrefs.edit().remove("pin").apply()
                userPrefs.edit().remove("isUidCreated").apply()
                dataUser.value = null
            } else {
                it.currentUser?.phoneNumber?.let {
                    viewModelScope.launch {
                        val response = api.getProfil(it.drop(3))
                        val isUidCreated = userPrefs.getBoolean("isUidCreated", false)

                        if (!isUidCreated){
                            userPrefs.edit().putBoolean("isUidCreated", true).apply()
                            auth.uid?.let { uid -> api.createIdDevice(it.drop(3), uid) }
                        }
                        if (response.isSuccessful) dataUser.value = response.body()
                        userPrefs.edit().putString("id", response.body()?.data?.id).apply()
                        userPrefs.edit().putString("pin", response.body()?.data?.pin).apply()

                        println(response.body())


                    }
                }
            }
        }

        fun eraseFCM(){
            viewModelScope.launch {
                api.destroyIdDevice(auth.currentUser?.phoneNumber!!.drop(3))
            }
        }


    }

    companion object{
        const val TAG = "UserDataVM"
    }

}