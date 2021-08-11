package com.sst.ngisiyuk.models.ngisiyuk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DataXXXXXX(
    val admin: Int,
    val code: String,
    val `data`: DataXXXXXXX,
    val dest: String,
    val detail: String,
    val expired: String,
    val id: String,
    val invoice: Int,
    val name: String,
    val period: @RawValue Any,
    val ref: @RawValue Any,
    val status: String,
    val total: Int,
    val type: String
) : Parcelable