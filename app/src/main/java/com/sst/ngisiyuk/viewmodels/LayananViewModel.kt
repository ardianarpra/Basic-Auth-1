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

    private val indosat = listOf(
        "0814",
        "0815",
        "0816",
        "0855",
        "0856",
        "0857",
        "0858",
    )
    private val xl = listOf(
        "0817",
        "0818",
        "0819",
        "0859",
        "0877",
        "0878",
    )
    private val axis = listOf(
        "0817",
        "0818",
        "0819",
        "0859",
        "0877",
        "0878",
    )
    private val three = listOf(
        "0895",
        "0896",
        "0897",
        "0898",
        "0899",

        )
    private val smartfren = listOf(
        "0881",
        "0882",
        "0883",
        "0884",
        "0887",
        "0888",
        "0889",
    )
    private val telkomsel = listOf(

        "0812",
        "0811",
        "0813",
        "0821",
        "0822",
        "0852",
        "0853",
        "0823",
        "0851",

        )
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

            println(response.body())
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

    fun getPulsaSubProduk(tipe: String, nomor: String) {
        println("nomor$nomor")


        if (nomor.length == 4){
           when{
               indosat.contains(nomor) -> {getListProduk(tipe, "Indosat")}
               xl.contains(nomor) -> {getListProduk(tipe, "XL")}
               axis.contains(nomor) -> {getListProduk(tipe, "Axis")}
               three.contains(nomor) -> {getListProduk(tipe, "Three")}
               smartfren.contains(nomor) -> {getListProduk(tipe, "Smartfren")}
               telkomsel.contains(nomor) -> {getListProduk(tipe, "Telkomsel")}
               else -> {}
           }
        }
    }


}