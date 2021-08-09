package com.sst.ngisiyuk.services


import com.sst.ngisiyuk.models.*
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("cek")
    suspend fun cekPenyediaJasa(
        @Field("no") no :String
    ):Response<CekPenyediaJasaModel>

    @FormUrlEncoded
    @POST("get_profil")
    suspend fun getProfilMitra(
        @Field("no") no :String
    ):Response<GetProfilModel>

    @FormUrlEncoded
    @POST("create_partner")
    suspend fun createProfil(
        @Field("no") no :String,
        @Field("nama") nama :String,
        @Field("alamat") alamat :String,
    ): Response<CreatePartnerModel>

    //    getHistory [id_partner]
    @FormUrlEncoded
    @POST("getHistory")
    suspend fun getHistory(
        @Field("id_partner") idPartner :String
    ):Response<GetHistoryModel>

    //    getHistory [kd_trans, kategori enkrip, tipe]
    @FormUrlEncoded
    @POST("getDetailHistory")
    suspend fun getDetailHistory(
        @Field("kd_trans") kdTrans :String
    ):Response<GetDetailHistoryModel>



    @FormUrlEncoded
    @POST("create_id_device")
    suspend fun createIDDevice(
        @Field("no") no :String,
        @Field("id") id :String,
        @Field("device") device :String,
    ): String

    @FormUrlEncoded
    @POST("destroy_id_device")
    suspend fun destroyIDDevice(
        @Field("no") no :String
    ):String

//    edit_profil [id_pj enkrip]
    @FormUrlEncoded
    @POST("edit_profil")
    suspend fun editProfil(
        @Field("id_pj") idPJ :String
    ):String



}