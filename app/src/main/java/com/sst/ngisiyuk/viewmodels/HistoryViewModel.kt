package com.sst.ngisiyuk.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sst.ngisiyuk.models.ngisiyuk.DataXXXXXXXX
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

    val historyTransaksiResponse = MutableLiveData<HistoryModel?>()


    fun getHistory(userId: String){
        viewModelScope.launch {

            val response :Response<HistoryModel> = api.getHistoryTransaksi(userId)

            if (response.isSuccessful) {
                historyTransaksiResponse.value = response.body()
                Log.d("getHistory", "getHistory: ${response.body()}")
            }
        }
    }

    fun getTopUpHistory(data : HistoryModel): MutableList<DataXXXXXXXX>{
        val topUpArray = mutableListOf<DataXXXXXXXX>()
        data.data.forEach {
            if (it.tipe == "topup") topUpArray.add(it)
        }

        return topUpArray
    }

    fun getPembelianHistory(data : HistoryModel): MutableList<DataXXXXXXXX>{
        val pembelian = mutableListOf<DataXXXXXXXX>()
        data.data.forEach {
            if (it.tipe == "pembelian") pembelian.add(it)
        }

        return pembelian
    }

    fun nullifyHistory() {
        historyTransaksiResponse.value = null
    }


}