package id.oc.okta.guestnote.database

data class NoteModel(val id: Long?, val nama: String?, val alamat: String?, val telepon: String?, val email: String?, val note: String?, val KategoriEvent: String?, val idEvent: String?) {
    companion object {
        const val TABLE_NOTE: String = "TABLE_NOTE"
        const val ID: String = "ID_"
        const val NAMA_GUEST: String = "NAMA_GUEST"
        const val ALAMAT_GUEST: String = "ALAMAT_GUEST"
        const val EMAIL_GUEST: String = "EMAIL_GUEST"
        const val TELEPON_GUEST: String = "TELEPON_GUEST"
        const val NOTE_GUEST: String = "NOTE_GUEST"
        const val KATEGORI_EVENT: String = "KATEGORI_EVENT_GUEST"
        const val ID_EVENT: String = "ID_EVENT"
    }
}