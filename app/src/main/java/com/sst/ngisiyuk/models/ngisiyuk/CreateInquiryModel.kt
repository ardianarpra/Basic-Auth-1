package com.sst.ngisiyuk.models.ngisiyuk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CreateInquiryModel(
    val `data`: DataXXXXX,
    val pesan: String,
    val status: Boolean
) : Parcelable