package com.sst.ngisiyuk.util

import android.view.View
import android.app.Activity

import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


interface HideKeyboard {

    fun hideKeyboard(view: View, activity: Activity){
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }
}