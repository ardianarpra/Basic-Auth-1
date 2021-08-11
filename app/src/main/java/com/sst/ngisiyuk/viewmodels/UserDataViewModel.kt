package com.sst.ngisiyuk.viewmodels

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
    private val api : NgisiyukServices
): ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val dataUser = MutableLiveData<GetProfil>()

    init {
        if (auth.currentUser != null){
            getUserProfile()
        }
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            auth.currentUser?.let{
                val response = api.getProfil(it.phoneNumber!!.drop(3))
                println(response.body())

                if (response.isSuccessful){
                    dataUser.value = response.body()
                }
            }
        }
    }

}