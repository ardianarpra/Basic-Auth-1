package com.sst.ngisiyuk.models.ngisiyuk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produk(
    val gambar: String,
    val tipe: String
):Parcelable