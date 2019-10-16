package id.oc.okta.guestnote.fitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.recyclerview.widget.LinearLayoutManager
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.adapter.EventAdapter
import id.oc.okta.guestnote.database.EventModel
import id.oc.okta.guestnote.database.database
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.WritableWorkbook
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.toolbar_kategori.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.io.File
import java.util.*
import jxl.write.Label
import android.widget.Toast
import id.oc.okta.guestnote.database.NoteModel
import id.oc.okta.guestnote.util.FileOpen


class EventActivity : AppCompatActivity() {

    private var eventsdata: MutableList<EventModel> = mutableListOf()
    private var eventsnote: MutableList<NoteModel> = mutableListOf()
    var Data: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        setSupportActionBar(findViewById(R.id.toolbar_kategori))
        val intent = intent.extras
        if (intent != null) {
            val intentData = intent.getString("Data")
            if (intentData != null) {
                Data = intentData
                TVTitle.text = intentData
            }
        }
        BtnBack.setOnClickListener {
            finish()
        }
        getDataEvent()
        getDataNote()
        BtnExport.setOnClickListener {
            createfileexcel()
        }
        RVEvent.layoutManager = LinearLayoutManager(this)
        RVEvent.adapter = EventAdapter(eventsdata, this)
        BtnAddEvent.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDataEvent() {
        eventsdata.clear()
        database.use {
            val result =
                    select(EventModel.TABLE_EVENT).whereArgs("${EventModel.KATEGORI_EVENT} = {noteId}", "noteId" to "$Data")
            val favorite = result.parseList(classParser<EventModel>())
            eventsdata.addAll(favorite)
            RVEvent.adapter?.notifyDataSetChanged()
        }
    }

    private fun getDataNote() {
        eventsnote.clear()
        database.use {
            val result =
                    select(NoteModel.TABLE_NOTE).whereArgs("${NoteModel.KATEGORI_EVENT} = {noteId}", "noteId" to "$Data")
            val favorite = result.parseList(classParser<NoteModel>())
            eventsnote.addAll(favorite)
            RVEvent.adapter?.notifyDataSetChanged()
        }
    }

    private fun createfileexcel() {
        var sd = Environment.getExternalStorageDirectory()
        var csvFile = "myData.xls"

        var directory = File(sd.absolutePath)

        if (!directory.isDirectory) {
            directory.mkdirs()
        }
        try {
            //file path
            val file = File(directory, csvFile)
            val wbSettings = WorkbookSettings()
            wbSettings.locale = Locale("en", "EN")
            val workbook: WritableWorkbook
            workbook = Workbook.createWorkbook(file, wbSettings)
            //Excel sheet name. 0 represents first sheet
            val sheet2 = workbook.createSheet("Note List $Data", 0)
            sheet2.addCell(Label(0, 0, "Nama"))
            sheet2.addCell(Label(1, 0, "Telepon"))
            sheet2.addCell(Label(2, 0, "Alamat"))
            sheet2.addCell(Label(3, 0, "Email"))
            sheet2.addCell(Label(4, 0, "Note"))
            sheet2.addCell(Label(5, 0, "Kategori"))
            var x2 = 0
            while (x2 < eventsnote.size) {
                sheet2.addCell(Label(0, x2 + 1, eventsnote[x2].nama))
                sheet2.addCell(Label(1, x2 + 1, eventsnote[x2].telepon))
                sheet2.addCell(Label(2, x2 + 1, eventsnote[x2].alamat))
                sheet2.addCell(Label(3, x2 + 1, eventsnote[x2].email))
                sheet2.addCell(Label(4, x2 + 1, eventsnote[x2].note))
                sheet2.addCell(Label(5, x2 + 1, eventsnote[x2].KategoriEvent))
                x2++
            }
            val sheet = workbook.createSheet("Event List $Data", 0)
            sheet.addCell(Label(0, 0, "Nama Event"))
            sheet.addCell(Label(1, 0, "Kontak"))
            sheet.addCell(Label(2, 0, "Alamat"))
            sheet.addCell(Label(3, 0, "Tanggal"))
            var x = 0
            while (x < eventsdata.size) {
                sheet.addCell(Label(0, x + 1, eventsdata[x].nama))
                sheet.addCell(Label(1, x + 1, eventsdata[x].kontak))
                sheet.addCell(Label(2, x + 1, eventsdata[x].alamat))
                sheet.addCell(Label(3, x + 1, eventsdata[x].tanggal))
                x++
            }
            workbook.write()
            workbook.close()
            Toast.makeText(
                    application,
                    "Data Exported in a Excel Sheet in Internal Storage", Toast.LENGTH_SHORT
            ).show()
            FileOpen.openFile(this, file)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        getDataEvent()
        getDataNote()
        super.onResume()
    }

}
