package com.sst.ngisiyuk.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.sst.ngisiyuk.R

class LoadingBar(context: Context) {
    private var builder: AlertDialog.Builder = AlertDialog.Builder(context)

    private var dialog: AlertDialog



    val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    @SuppressLint("InflateParams")
    val alertView: View = layoutInflater.inflate(R.layout.show_dialog_item, null)


    val alertLoginDialog = AlertDialog.Builder(context).setView(R.layout.show_dialog_item).create()


    init {
        builder.setView(R.layout.progress_dialog)

        dialog = builder.create()
        alertLoginDialog.setView((alertView))


    }

    fun showAlert(bool:Boolean) {
        dialog.setCancelable(bool)

        dialog.show()

    }

    fun closeAlert (){
       dialog.cancel()
    }

    fun showAlertLogin(bool: Boolean){
        alertLoginDialog.setCancelable(bool)
        alertLoginDialog.show()
    }

    fun closeLoginAlert(){
        alertLoginDialog.dismiss()
    }




}