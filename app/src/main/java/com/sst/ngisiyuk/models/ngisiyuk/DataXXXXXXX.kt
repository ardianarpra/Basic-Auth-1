package com.sst.ngisiyuk.models.ngisiyuk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataXXXXXXX(
    val distribution: String,
    val fare: String,
    val idpel: String,
    val maxkwhlimit: String,
    val nometer: String,
    val serviceunit: String
) : Parcelable