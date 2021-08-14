package com.sst.ngisiyuk.models.ngisiyuk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DataXXXXX(
    val `data`: DataXXXXXX,
    val harga_admin: Int,
    val harga_invoice: Int,
    val harga_total: String,
    val id_pelanggan: String,
    val kode_inquiry: String,
    val kode_produk: String,
    val nama_ppob: String
) : Parcelable