package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_root_add.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class RootAñadirActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_add)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnCines.setOnClickListener {
            //Launch activity add categoria
            val intent = Intent(this, NewCategoriaActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnNewPelicula.setOnClickListener {
            //Launch activity add pelicula
            val intent = Intent(this, NewPeliculaActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnNewProvincia.setOnClickListener {
            //Launch activity add provincia
            val intent = Intent(this, NewProvinciaActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnNewCine.setOnClickListener {
            //Launch activity add cine
            val intent = Intent(this, NewCineActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }
    }
}
