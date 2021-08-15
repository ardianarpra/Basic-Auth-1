package com.sst.ngisiyuk.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.sst.ngisiyuk.R

class LoadingBar(context: Context)  {
    private var builder: AlertDialog.Builder = AlertDialog.Builder(context)
    val loading: AlertDialog = builder.apply {
        setView(R.layout.progress_dialog)
    }.create()

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    @SuppressLint("InflateParams")


    fun showAlert(bool:Boolean) {
        loading.setCancelable(bool)
        loading.show()

    }

    fun closeAlert (){
       loading.dismiss()
    }



}