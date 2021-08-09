package com.sst.ngisiyuk.di

import com.google.gson.GsonBuilder
import com.sst.ngisiyuk.repositories.ApiServicesRepo
import com.sst.ngisiyuk.services.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val baseURL = "https://admin.hokygo.com/api_jasa/"

    @Singleton
    @Provides
    fun provideOkhttp(): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()


    @Singleton
    @Provides
    fun provideRetrofit (okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
            .setLenient()
            .create()))
        .client(okHttpClient)
        .baseUrl(baseURL)
        .build()

    @Singleton
    @Provides
    fun provideServices(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Singleton
    @Provides
    fun providesRepository(api :ApiServices) = ApiServicesRepo(api)

}