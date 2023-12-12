package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_root_delete.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class RootDeleteActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_delete)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnDeleteCategoria.setOnClickListener {
            //Launch activity delete categoria
            val intent = Intent(this, DeleteCategoriaActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnDeletePelicula.setOnClickListener {
            //Launch activity delete pelicula
            val intent = Intent(this, DeletePeliculaEnActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnDeleteProvincia.setOnClickListener {
            //Launch activity delete provincia
            val intent = Intent(this, DeleteProvinciaActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnDeleteCine.setOnClickListener {
            //Launch activity delete cine
            val intent = Intent(this, DeleteCineActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }
    }
}
