package com.sst.ngisiyuk.models.request


class CreateRequestModel {
    var idCreation :String = ""
    var alamatLengkap: String? = ""

    var dataJasa: DataJasa = DataJasa()
    var idLayananJasa:String = ""
    var namaLayananJasa : String =""
    var detailStatus: List<DetailStatu> = listOf(DetailStatu())
    var hargaLelang: Int = 0
    var idPartner: String = ""
    var idPelanggan: String = ""
    var jarak: Int = 0
    var lat: Double = 0.0
    var lelang: List<Lelang> = listOf(Lelang())
    var long: Double = 0.0
    var noTelp: String = ""
    var status: String = ""
    var tglButuh: String = ""
    var tglCreateRequest: String = "null"
}