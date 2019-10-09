package id.oc.okta.guestnote.fitur

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.database.NoteModel
import id.oc.okta.guestnote.database.database
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.custom_alert_dialog_success.*
import kotlinx.android.synthetic.main.custom_alert_dialog_success.view.*
import org.jetbrains.anko.db.insert

class AddNoteActivity : AppCompatActivity() {

    var Data: String? = null
    var idEvent: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Add Note"
        val intent = intent.extras
        if (intent != null) {
            val intentData = intent.getString("IdEvent")
            val intentDatas = intent.getString("Data")
            if (intentData != null) {
                idEvent = intentData
                Data = intentDatas
            }
        }
        BtnSubmitNote.setOnClickListener {
            actionaddNote()
            showDialogs()
        }
    }

    private fun showDialogs() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog_success, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Notification")
        //show dialog
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mAlertDialog.setCancelable(false)
        //login button click of custom layout
        mDialogView.BtnDialog.setOnClickListener {
            mAlertDialog.dismiss()
            finish()
        }
    }

    private fun actionaddNote() {
        when {
            ETNamaNote.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Nama Guest Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            PhoneNote.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Telepon Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            ETAlamatNote.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Alamat Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            ETEmailNote.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Email Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            ETNote.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Note Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            else -> {
                database.use {
                    insert(
                            NoteModel.TABLE_NOTE,
                            NoteModel.NAMA_GUEST to ETNamaNote.text.toString(),
                            NoteModel.ALAMAT_GUEST to ETAlamatNote.text.toString(),
                            NoteModel.EMAIL_GUEST to ETEmailNote.text.toString(),
                            NoteModel.TELEPON_GUEST to PhoneNote.text.toString(),
                            NoteModel.NOTE_GUEST to ETNote.text.toString(),
                            NoteModel.KATEGORI_EVENT to Data,
                            NoteModel.ID_EVENT to idEvent
                    )
                    Toast.makeText(applicationContext, "Data Berhasil Masuk", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
