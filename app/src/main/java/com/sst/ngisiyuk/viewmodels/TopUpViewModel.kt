package com.sst.ngisiyuk.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sst.ngisiyuk.models.ngisiyuk.DataXXXXXXXXXX
import com.sst.ngisiyuk.models.ngisiyuk.ListBankPerusahaanModel
import com.sst.ngisiyuk.models.ngisiyuk.TopUpSaldoModel
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(
    val api :NgisiyukServices,
): ViewModel() {

    val topUpResponse = MutableLiveData<TopUpSaldoModel?>()
    val listBankPerusahaan = MutableLiveData<ListBankPerusahaanModel>()
    val chosenBank = MutableLiveData<DataXXXXXXXXXX?>()

    fun topUpSaldo(id: String, id_b: String, saldo: String) {
        viewModelScope.launch {
            val response = api.topUpSaldo(id, id_b, saldo)

            if (response.isSuccessful) topUpResponse.value = response.body()
        }
    }
    fun getListBank(){
        viewModelScope.launch {
            val response = api.getListBankPerusahaan()

            if (response.isSuccessful) listBankPerusahaan.value = response.body()
        }
    }

    fun setChosenBank(chosen : DataXXXXXXXXXX){
        chosenBank.value = chosen
    }

    fun nullifyEveryThing(){
        topUpResponse.value = null
        chosenBank.value = null
    }
}