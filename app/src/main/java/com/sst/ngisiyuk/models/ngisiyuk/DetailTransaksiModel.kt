package com.sst.ngisiyuk.models.ngisiyuk

data class DetailTransaksiModel(
    val harga_total: String,
    val invoice: String,
    val nama_produk: String,
    val provider: String,
    val status: String,
    val tgl_trans: String,
    val tipe: String,
    val tujuan: String
)