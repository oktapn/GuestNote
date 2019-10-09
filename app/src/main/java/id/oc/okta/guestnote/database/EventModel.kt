package id.oc.okta.guestnote.database

data class EventModel(val id: Long?, val nama: String?, val alamat: String?, val kategori: String?, val image: String?, val tanggal: String?, val kontak: String?) {
    //nama,tanggal,alamat,kontak,kategori
    companion object {
        const val TABLE_EVENT: String = "TABLE_EVENT"
        const val ID: String = "ID_"
        const val NAMA_EVENT: String = "NAMA_EVENT"
        const val ALAMAT_EVENT: String = "ALAMAT_EVENT"
        const val KONTAK_EVENT: String = "KONTAK_EVENT"
        const val KATEGORI_EVENT: String = "KATEGORI_EVENT"
        const val TANGGAL_EVENT: String = "TANGGAL_EVENT"
        const val IMAGE_EVENT: String = "IMAGE_EVENT"
    }
}