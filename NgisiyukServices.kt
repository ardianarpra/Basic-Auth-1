package com.sst.ngisiyuk.services

import com.sst.ngisiyuk.models.ngisiyuk.*
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST

interface NgisiyukServices {

    @FormUrlEncoded
    @POST("create_profil")
    suspend fun createProfil(
        @Field("no") no :String,
        @Field("nama") nama :String,
        @Field("alamat") alamat :String,
        @Field("pin") pin :String,
        @Field("referral") referral :String,
    ):Response<CreateProfil>

    @FormUrlEncoded
    @POST("cek_pelanggan")
    suspend fun cekPelanggan(
        @Field("no") no :String,
    ):Response<CekPelanggan>

    @FormUrlEncoded
    @POST("get_profil")
    suspend fun getProfil(
        @Field("no") no :String,
    ):Response<GetProfil>

    @FormUrlEncoded
    @POST("change_pin")
    suspend fun changePin(
        @Field("id") id :String,
        @Field("pin") pin :String,
    ):Response<GetProfil>

    @FormUrlEncoded
    @POST("create_id_device")
    suspend fun createIdDevice(
        @Field("id_pelanggan") id_pelanggan :String,
        @Field("id_device") id_device :String,
    ):Response<CreateIdDevice>

    @FormUrlEncoded
    @POST("destroy_id_device")
    suspend fun destroyIdDevice(
        @Field("no") no :String,
    ):Response<DestroyIdDevice>

    @FormUrlEncoded
    @POST("update_parent")
    suspend fun updateParent(
        @Field("id") id :String,
        @Field("parent") parent :String,
    ):Response<GetProfil>


    @POST("get_layanan")
    suspend fun getLayanan():Response<GetLayananModel>

    @FormUrlEncoded
    @POST("edit_profil")
    suspend fun editProfil(
        @Field("id") id:String, // enkrip
    )

    @FormUrlEncoded
    @POST("update_profil")
    suspend fun updateProfil(
        @Field("id") id:String, // engkrp
        @Field("no") no:String,
        @Field("nama") nama:String,
        @Field("jk") jk:String, // 0 laki" , 1 perempuan
        @Field("ft") ft:Multipart,
        @Field("alamat") alamat:String,
    ) :Response<UpdateProfileModel>

    @FormUrlEncoded
    @POST("isi_layanan")
    suspend fun isiLayanan(
        @Field("tipe") tipe:String,
    ) : Response<IsiLayananModel>

    @FormUrlEncoded
    @POST("list_produk")
    suspend fun listProduk(
        @Field("tipe") tipe:String,
        @Field("provider") provider:String,
    ): Response<ListProdukModel>

    @FormUrlEncoded
    @POST("asset_pelanggan")
    suspend fun assetPelanggan(
        @Field("id") id:String, // id engkrip
        @Field("kd") kd:String, // kd_engkrip
    ): Response<AssetPelanggan>

    @FormUrlEncoded
    @POST("create_inquiry")
    suspend fun createInquiry(
        @Field ("id_pelanggan")idPelanggan: String,
        @Field ("kode")kode: String,
        @Field ("tujuan")tujuan: String
    ) : Response<CreateInquiryModel>

    @FormUrlEncoded
    @POST("createTransPembelian")
    suspend fun createTransPembelian(
        @Field ("id_pelanggan") idPelanggan :String,
        @Field("id_keuntungan") idKeuntungan:String,
        @Field("tujuan") tujuan: String
    ) : Response<TransPembelianModel>

    @FormUrlEncoded
    @POST("createTransPPOB")
    suspend fun createTransPPOB(
        @Field ("id_pelanggan") idPelanggan :String,
        @Field("id_inquiry") idInquiry:String,
        @Field("harga_total") hargaTotal: String
    ) : Response<CreateTransPPOBModel>

    @FormUrlEncoded
    @POST("history_transaksi")
    suspend fun getHistoryTransaksi(
        @Field("id") id:String
    ): Response<HistoryModel>

    @FormUrlEncoded
    @POST("verifyTransPembelian")
    suspend fun verivyTransPembelian(
        @Field("invoice") kode:String
    ): Response<VerifyTransPembelianResponseModel>

    @FormUrlEncoded
    @POST("")
    suspend fun getDetailTransaksi(
        @Field("id") id:String,
        @Field("tipe") tipe:String
    ) :Response<DetailTransaksiModel>

    /* API DIBAWAH ADALAH API UNTUK TOP UP DAN TRANSFER SALDO */

    @FormUrlEncoded
    @POST("topupsaldo")
    suspend fun topUpSaldo(
        @Field("id") id :String,
        @Field("id_b") id_b :String,
        @Field("saldo") saldo :String,
    ): Response<TopUpSaldoModel>


    @POST("listbank")
    suspend fun getListBankPerusahaan(): Response<ListBankPerusahaanModel>

    @FormUrlEncoded
    @POST("readActivitySaldoApi")
    suspend fun readActivitySaldo(
        @Field("id") id: String,
        @Field("date_start") date_start: String, // 2021-06-10
        @Field("date_end") date_end: String, // 2021-08-20
    ): Response<ReadActivitySaldoModel>

    @FormUrlEncoded
    @POST("transferSaldoApi")
    suspend fun createTransferSaldo(
        @Field("dari") dari :String,
        @Field("kepada") kepada :String,
        @Field("jumlah") jumlah :String,
    ): Response<ResponseTransferSaldoModel>

}