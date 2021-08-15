package com.sst.ngisiyuk.models.ngisiyuk

data class DataXXXXXXXXXXX(
    val date_end: String,
    val date_start: String,
    val referal: List<Referal>,
    val topup: Topup,
    val transfer: List<Transfer>
)