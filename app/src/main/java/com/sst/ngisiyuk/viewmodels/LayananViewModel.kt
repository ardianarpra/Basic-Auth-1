package com.sst.ngisiyuk.viewmodels

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sst.ngisiyuk.models.ngisiyuk.GetLayananModel
import com.sst.ngisiyuk.models.ngisiyuk.IsiLayananModel
import com.sst.ngisiyuk.models.ngisiyuk.ListProdukModel
import com.sst.ngisiyuk.models.ngisiyuk.Produk
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayananViewModel @Inject constructor(
    private val api : NgisiyukServices
):ViewModel() {


    val allServices = MutableLiveData<ArrayList<Produk>>()
    val subProduct = MutableLiveData<ListProdukModel>()
    val isiLayanan = MutableLiveData<IsiLayananModel?>()

    private val layanan = arrayListOf<Produk>()
    init{
        getLayanan()
    }

    private fun getLayanan() {
        viewModelScope.launch {
            val response = api.getLayanan()
            if (response.isSuccessful)  {
                response.body()?.data?.produk?.forEach {
                    layanan.add(it)
                }
                response.body()?.data?.tagihan?.forEach {
                    layanan.add(it)
                }

                allServices.value = layanan
            }
        }
    }

    fun getListProduk(tipe: String, provider:String){
        viewModelScope.launch {
            val response = api.listProduk(tipe, provider)

            subProduct.value = response.body()
        }
    }

    fun getIsiLayanan(tipe: String){
        viewModelScope.launch {
            val response = api.isiLayanan(tipe)

            isiLayanan.value = response.body()
        }
    }

    fun nullifySubLayanan() {
        isiLayanan.value = null
    }

    fun filterNumberAndGetLayanan(nomor: Editable) {

    }


}