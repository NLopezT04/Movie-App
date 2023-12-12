package net.azarquiel.appfinal_nestorlopez.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.azarquiel.appfinal_nestorlopez.R

class UbicacionCineActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latitudCine : Double? = 0.0
    private var longitudCine : Double? = 0.0
    private var nombreCine : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion_cine)

        latitudCine = intent.getSerializableExtra("latitud") as? Double
        longitudCine = intent.getSerializableExtra("longitud") as? Double
        nombreCine = intent.getSerializableExtra("nombre") as? String

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney, Australia, and move the camera.
        val location = LatLng(latitudCine!!, longitudCine!!)
        mMap.addMarker(MarkerOptions().position(location).title("$nombreCine"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))

    }
}

