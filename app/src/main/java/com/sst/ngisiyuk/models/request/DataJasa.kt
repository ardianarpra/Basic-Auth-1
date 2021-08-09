package com.sst.ngisiyuk.models.request


class DataJasa {


    var namaMakanan: String? = null
    var jumlahPorsi: String? = null
    var jenisCatering: String? = null
    var usia: String? = null
    var tingkatKemahiran: String? = null
    var frekuensi: String? = null
    var kendaraan: String? = null
    var deskripsi: String? = null
    var genderPartner: String? = null
    var jenisDurasi: String? = null
    var lamaDurasi: String? = null

    /* ini untuk kategori pemasangan */

    var jumlahUnit :String? = null
    var jenisProperti:String? = null
    var lokasiPerbaikan :String? = null

    /*untuk jasa antar*/

    var origin : LatLong? = null
    var destination : LatLong? = null

    /* untuk jasa perbaikan*/
    var keluhan: String? = null
    var imageString: String? = null

    /* untuk jasa kursus les*/

    var tingkatPendidikan: String? = null
    var mataPelajaran: String? = null


}