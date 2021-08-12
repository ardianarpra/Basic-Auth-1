package com.sst.ngisiyuk.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.models.ngisiyuk.HistoryModel
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val api:NgisiyukServices,

):ViewModel() {

    val historyTransaksiResponse = MutableLiveData<HistoryModel>()


    fun getHistory(userId: String){
        viewModelScope.launch {

            val response :Response<HistoryModel> = api.getHistoryTransaksi(userId)

            if (response.isSuccessful) {
                historyTransaksiResponse.value = response.body()
                Log.d("getHistory", "getHistory: ${response.body()}")
            }
        }
    }
}