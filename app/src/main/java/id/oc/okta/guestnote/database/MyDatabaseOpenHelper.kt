package id.oc.okta.guestnote.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "GuestNote.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            EventModel.TABLE_EVENT, true,
            EventModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            EventModel.NAMA_EVENT to TEXT,
            EventModel.ALAMAT_EVENT to TEXT,
            EventModel.KATEGORI_EVENT to TEXT,
            EventModel.IMAGE_EVENT to TEXT,
            EventModel.TANGGAL_EVENT to TEXT,
            EventModel.KONTAK_EVENT to TEXT
        )

        db.createTable(
            NoteModel.TABLE_NOTE, true,
            NoteModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            NoteModel.NAMA_GUEST to TEXT,
            NoteModel.ALAMAT_GUEST to TEXT,
            NoteModel.TELEPON_GUEST to TEXT,
            NoteModel.EMAIL_GUEST to TEXT,
            NoteModel.NOTE_GUEST to TEXT,
            NoteModel.KATEGORI_EVENT to TEXT,
            NoteModel.ID_EVENT to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(EventModel.TABLE_EVENT, true)
        db.dropTable(NoteModel.TABLE_NOTE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)