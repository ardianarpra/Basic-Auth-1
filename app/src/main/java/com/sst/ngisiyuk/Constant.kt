package com.sst.ngisiyuk

import com.sst.ngisiyuk.models.ngisiyuk.MenuAkun

object Constant {
    val menuAkun = arrayListOf<MenuAkun>(
        MenuAkun("Saldo", R.drawable.ic_saldo),
        MenuAkun("Ajak Teman", R.drawable.ic_ajak_teman),
        MenuAkun("Ringkasan Akun", R.drawable.ic_ringkasan_akun),
        MenuAkun("Pengaturan Akun", R.drawable.ic_detil_akun),
        MenuAkun("Kebijakan Privacy", R.drawable.ic_kebijakan_privacy),
        MenuAkun("Log Out", R.drawable.ic_log_out),
    )

    val ringkasanAkun = arrayListOf<MenuAkun>(

    )
}