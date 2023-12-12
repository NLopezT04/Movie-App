package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_cines.*
import net.azarquiel.appfinal_nestorlopez.Class.Cine
import net.azarquiel.appfinal_nestorlopez.R

class DetailCinesActivity : AppCompatActivity() {

    private lateinit var cine:Cine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cines)

        cine = intent.getSerializableExtra("cine") as Cine
        Log.d("CINE","Cine Recogido $cine")

        pintar()

        btnVerUbicacion.setOnClickListener {
            //Ver ubicacion en Google Maps
            val intent = Intent(this,UbicacionCineActivity::class.java)
            intent.putExtra("latitud",cine.latitud)
            intent.putExtra("longitud",cine.longitud)
            intent.putExtra("nombre",cine.nombre)
            startActivity(intent)
        }

        btnVerListaPeliculas.setOnClickListener {
            //Pasar cine para ver las peliculas
            val intent = Intent(this, ListaPeliculasEnCineActivity::class.java)
            intent.putExtra("cineEntero",cine)
            startActivity(intent)
        }
    }

    private fun pintar(){
        tvTituloCine.text = cine.nombre
        tvDireccionCine.text = cine.direccion
    }
}
