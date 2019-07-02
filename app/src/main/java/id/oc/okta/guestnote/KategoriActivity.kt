package id.oc.okta.guestnote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class KategoriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        setSupportActionBar(findViewById(R.id.toolbar_kategori))
    }
}
