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

        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null){
                userPrefs.edit().remove("id").apply()
                userPrefs.edit().remove("pin").apply()
                userPrefs.edit().remove("isUidCreated").apply()
                dataUser.value = null

                println("LogOut")
            } else {
                firebaseAuth.currentUser?.phoneNumber?.let {
                    getUserData()
                }
            }
        }






    }

    fun eraseFCM(){
        viewModelScope.launch {
            api.destroyIdDevice(auth.currentUser?.phoneNumber!!.drop(3))
        }
    }

    fun getUserData(){
        auth.currentUser?.phoneNumber?.let {
            viewModelScope.launch {
                println("DI vmScope : ${it.drop(3)}")
                val response = api.getProfil(it.drop(3))

                if (response.isSuccessful) {
                    dataUser.value = response.body()
                    userPrefs.edit().putString("id", response.body()?.data?.id).apply()
                    userPrefs.edit().putString("pin", response.body()?.data?.pin).apply()
                }

                println(response.body()?.data?.pin)
            }
        }
    }

    companion object{
        const val TAG = "UserDataVM"
    }

}