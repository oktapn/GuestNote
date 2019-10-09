package id.oc.okta.guestnote.fitur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.oc.okta.guestnote.BuildConfig
import id.oc.okta.guestnote.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar_main.*



class AboutActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        BtnAbout.visibility = View.GONE
        BtnBackNav.visibility = View.VISIBLE
        textView.text = BuildConfig.VERSION_NAME
        BtnBackNav.setOnClickListener {
            onBackPressed()
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-6.189976, 106.802064)
        mMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                sydney,
                12.0f
            ))
    }
}
