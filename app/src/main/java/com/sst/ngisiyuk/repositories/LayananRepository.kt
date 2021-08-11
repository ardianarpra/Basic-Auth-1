package com.sst.ngisiyuk.repositories

import com.sst.ngisiyuk.models.ngisiyuk.ListProdukModel
import com.sst.ngisiyuk.services.NgisiyukServices
import com.sst.ngisiyuk.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@ActivityScoped
class LayananRepository @Inject constructor(
    private val api : NgisiyukServices
) {
    suspend fun getListProduk(tipe: String, provider: String): Resource<Response<ListProdukModel>>{
        val response = try {
            api.listProduk(tipe, provider)
        } catch (e:Exception){
            return Resource.Error("Something went wrong")
        }

        return Resource.Success(response)
    }

}