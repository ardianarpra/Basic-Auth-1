package com.sst.ngisiyuk.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.databinding.InputPinLayoutBinding
import com.sst.ngisiyuk.repositories.ApiServicesRepo
import com.sst.ngisiyuk.repositories.LayananRepository
import com.sst.ngisiyuk.services.ApiServices
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val baseURL = "https://admin.ngisiyuk.com/api/"

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
    fun provideUserDataPrefs(application: Application): SharedPreferences = application.getSharedPreferences("UserData", 0)


    @Singleton
    @Provides

    fun provideIdUser(userPref : SharedPreferences) :String = userPref.getString("id", "")!!


//    @Singleton
//    @Provides
//    @Named("pin")
//    fun providePin(userPref : SharedPreferences) :String = userPref.getString("pin", "")!!

    @Singleton
    @Provides
    fun provideServices(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Singleton
    @Provides
    fun providesRepository(api :ApiServices) = ApiServicesRepo(api)

    @Singleton
    @Provides
    fun providesNgisiyuk(retrofit: Retrofit):NgisiyukServices = retrofit.create(NgisiyukServices::class.java)

    @Singleton
    @Provides
    fun provideLayananProvider(ngisiyukServices: NgisiyukServices) = LayananRepository(ngisiyukServices)

    @Singleton
    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()


}