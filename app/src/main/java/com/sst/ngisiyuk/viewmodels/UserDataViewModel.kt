package com.sst.ngisiyuk.viewmodels

import android.annotation.SuppressLint
import android.content.SharedPreferences
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


    @SuppressLint("ApplySharedPref")
    fun getUserProfile() {
        viewModelScope.launch {
            auth.currentUser?.let{
                val response = api.getProfil(it.phoneNumber!!.drop(3))
                println(response.body())

                if (response.isSuccessful){
                    dataUser.value = response.body()
                    userPrefs.edit().putString("id", response.body()?.data?.id).commit()
                }
            }
        }
    }

    fun nullifyDataUser(){
        dataUser.value = null
    }

}