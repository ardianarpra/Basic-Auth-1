package com.sst.ngisiyuk.models

data class GetDetailHistoryModel(
    val kd_trans: String,
    val nominal: String,
    val status: String,
    val tgl_transaksi: String,
    val tracking: List<Tracking>
)