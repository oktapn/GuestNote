package id.oc.okta.guestnote.fitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import id.oc.okta.guestnote.BuildConfig
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.adapter.KategoriAdapater
import id.oc.okta.guestnote.model.Kategori
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity() {

    val kategoris: ArrayList<Kategori> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addKategori()
        setSupportActionBar(findViewById(R.id.toolbar_main))
        TVversion.text = (BuildConfig.VERSION_NAME)
        recyclerView.layoutManager = GridLayoutManager(
            this,
            2,
            GridLayoutManager.VERTICAL,
            false
        );
        recyclerView.adapter = KategoriAdapater(kategoris, this)
        BtnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addKategori() {
        kategoris.add(0, Kategori("Sport","https://media.gettyimages.com/photos/sports-heroes-picture-id502301173?s=612x612"))
        kategoris.add(1, Kategori("Music","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqZoi_kK2hgPef9CiSFZ-z3EjCqwAO5-SP-RDRr-liseQp7n-4"))
        kategoris.add(2, Kategori("IT","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPWSB3stiOF9Unhs_yAGgM3clcxT94cgLFrRokGvzZt5xDAFKD"))
        kategoris.add(3, Kategori("Finance","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCLyT2hbQ9KSqxkGTFv78AlWDZcR4d6W-v5EFJ7qwIb9kMXuTv"))
        kategoris.add(4, Kategori("Lifestyle","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJa6lJqhCH7u4IGiAuEvWEUYaLhj8hlhr5OsWCp8F0cc-EJDQe"))
        kategoris.add(5, Kategori("Health","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR803Gj6O_qJcI1Bvo3tFUafVMS2kVnCwMgippBvqmOkNCRQk-i"))
    }
}
