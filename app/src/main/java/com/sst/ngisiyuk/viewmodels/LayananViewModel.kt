package com.sst.ngisiyuk.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sst.ngisiyuk.models.ngisiyuk.*
import com.sst.ngisiyuk.repositories.LayananRepository
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayananViewModel @Inject constructor(
    private val api : NgisiyukServices,
    private val layananRepo :LayananRepository
):ViewModel() {


    val allServices = MutableLiveData<ArrayList<Produk>>()
    val subProduct = MutableLiveData<ListProdukModel?>()
    val isiLayanan = MutableLiveData<IsiLayananModel?>()
    val createInquiryResponse = MutableLiveData<CreateInquiryModel?>()
    val transPembelianResponse = MutableLiveData<TransPembelianModel?>()
    val verifyTransPembelianResponse = MutableLiveData<VerifyTransPembelianResponseModel>()
    val transPPOBResponse = MutableLiveData<CreateTransPPOBModel>()


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
            val response = layananRepo.getListProduk(tipe, provider)
            if(response.data == null){

            } else subProduct.value = response.data

            println("tipe:$tipe, provider:$provider, data: ${response.data}")
        }

    }

    fun getIsiLayanan(tipe: String){
        viewModelScope.launch {
            val response = api.isiLayanan(tipe)
            isiLayanan.value = response.body()
            println(Gson().toJson(response.body()))
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

    fun nullifySubProduk() {
        subProduct.value = null
    }

    fun createInquiry(idPelanggan:String , kode:String, tujuan:String){
        println("idPel : $idPelanggan, kode:$kode, tujuan: $tujuan")
        viewModelScope.launch {
            val response = api.createInquiry(idPelanggan, kode, tujuan)

            if (response.isSuccessful) createInquiryResponse.value = response.body()
        }
    }

    fun createTransPembelian(idPelanggan: String, idKeuntungan: String, tujuan: String) {
        viewModelScope.launch {
            val response = api.createTransPembelian(idPelanggan, idKeuntungan , tujuan )

            if (response.isSuccessful) transPembelianResponse.value = response.body()

            println("Tes PPOB : ${response.body()}")
        }
    }

    fun createTransPPOB(idPelanggan: String, idKeuntungan: String, hargaTotal: String) {

        println("idPelanggan:$idPelanggan, idKeuntungan:$idKeuntungan, tujuan: $hargaTotal")
        viewModelScope.launch {
            val response = api.createTransPPOB(idPelanggan, idKeuntungan , hargaTotal )

            if (response.isSuccessful) transPPOBResponse.value = response.body()

            println("Tes PPOB : ${response.body()}")
        }
    }

    fun nullifyStatusPembelian() {
        transPembelianResponse.value = null
    }

    fun nullifyInquiryResponse() {
        createInquiryResponse.value = null
    }


}