package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_pelicula_en_cines.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class DeletePeliculaEnCinesActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pelicula_en_cines)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnBorrarPeliculaCine.setOnClickListener {
            removePeliculaToFirebaseDatabase()
            limpiar()
        }

        imgHome.setOnClickListener {
            //viajar a la home
            val intent = Intent(this,DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }
    }

    private fun removePeliculaToFirebaseDatabase() {
        val cprovincia = tvDeleteIdProvincia.text.toString().toInt()
        val ccine = tvDeleteIdCine.text.toString().toInt()
        val cpeliEnCine = tvDeleteIdPelicula.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("provincias/$cprovincia/listaCines/$ccine/listaPeliculas").child(cpeliEnCine)

        ref.removeValue()
        Toast.makeText(this,"Pelicula eliminada de cines correctamente", Toast.LENGTH_SHORT).show()
    }

    private fun limpiar(){
        tvDeleteIdProvincia.text.clear()
        tvDeleteIdCine.text.clear()
        tvDeleteIdPelicula.text.clear()
    }
}
