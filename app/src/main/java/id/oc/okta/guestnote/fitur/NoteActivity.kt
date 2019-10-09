package id.oc.okta.guestnote.fitur

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.util.Base64
import com.bumptech.glide.Glide
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.adapter.NoteAdapter
import id.oc.okta.guestnote.database.EventModel
import id.oc.okta.guestnote.database.NoteModel
import id.oc.okta.guestnote.database.database
import id.oc.okta.guestnote.model.Note
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


class NoteActivity : AppCompatActivity() {

    val notes: ArrayList<Note> = ArrayList()
    var listnotes: MutableList<NoteModel> = mutableListOf()
    var title: String? = null
    var image: String? = null
    var idEvent: String? = null
    var Data: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
//        addNote()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        val intent = intent.extras
        if (intent != null) {
            val intentData = intent.getString("Data")
            val intentDatas = intent.getString("IdEvent")
            if (intentData != null) {
                collapsingToolbarLayout.title = intentData
                title = intentData
                idEvent = intentDatas
            }
        }

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.colorAccent)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.transparent)
        )
        getDataNote()
        RVNote.layoutManager = LinearLayoutManager(this)
        RVNote.adapter = NoteAdapter(listnotes, this)
        BtnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("IdEvent", idEvent)
            intent.putExtra("Data", Data)
            startActivity(intent)
        }
        loadimage()
    }

    private fun getDataNote() {
        listnotes.clear()
        database.use {
            val result =


                    select(NoteModel.TABLE_NOTE).whereArgs("${NoteModel.ID_EVENT} = {noteId}", "noteId" to "${idEvent?.toLong()}")
            val favorite = result.parseList(classParser<NoteModel>())
            listnotes.addAll(favorite)
            RVNote.adapter?.notifyDataSetChanged()
        }

    }

    private fun loadimage() {
        database.use {
            val result = select(EventModel.TABLE_EVENT).whereArgs("${EventModel.NAMA_EVENT} = {image}", "image" to "$title")
            val favorite = result.parseList(classParser<EventModel>())
            image = favorite[0].image
            Data = favorite[0].kategori
        }

        Glide.with(applicationContext).load(stringToBitMap(image)).into(IVNoteEvent)
    }

    private fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun addNote() {
        notes.add(0, Note("OKta", "EventModel yang menarik mungkin kedepannya harga tiket masuk di murahkan 1"))
        notes.add(1, Note("Ota", "EventModel yang menarik mungkin kedepannya harga tiket masuk di murahkan 2"))
        notes.add(2, Note("OKa", "EventModel yang menarik mungkin kedepannya harga tiket masuk di murahkan 3"))
        notes.add(3, Note("Oa", "EventModel yang menarik mungkin kedepannya harga tiket masuk di murahkan 4"))
        notes.add(4, Note("Ok", "EventModel yang menarik mungkin kedepannya harga tiket masuk di murahkan 5"))
        notes.add(5, Note("Ot", "EventModel yang menarik mungkin kedepannya harga tiket masuk di murahkan 6"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        getDataNote()
    }
}
