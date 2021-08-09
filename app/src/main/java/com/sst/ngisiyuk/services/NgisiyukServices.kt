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
        @Field("no") no :String,
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








}