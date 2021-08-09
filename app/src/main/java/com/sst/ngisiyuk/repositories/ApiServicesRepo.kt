package com.sst.ngisiyuk.repositories

import com.sst.ngisiyuk.models.*
import com.sst.ngisiyuk.services.ApiServices
import com.sst.ngisiyuk.util.Resource

import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class ApiServicesRepo @Inject constructor(
    private val api : ApiServices
) {
    suspend fun cekPenyediaJasa(no:String): Resource<Response<CekPenyediaJasaModel>> {
        val response = try{
            api.cekPenyediaJasa(no)
        } catch (e:Exception){
            return Resource.Error("Error Occured")
        }

        return Resource.Success(response)
    }


    suspend fun getProfilMitra(no:String):Resource<Response<GetProfilModel>>{
        val response = try{
            api.getProfilMitra(no)
        } catch (e:Exception){
            return Resource.Error("Error Occured")
        }



        return Resource.Success(response)
    }

    suspend fun createProfilMitra(no:String, nama:String, alamat:String):Resource<Response<CreatePartnerModel>>{
        val response = try{
            api.createProfil(no, nama, alamat)
        } catch (e:Exception){
            return Resource.Error("Error Occured")
        }



        return Resource.Success(response)
    }
}