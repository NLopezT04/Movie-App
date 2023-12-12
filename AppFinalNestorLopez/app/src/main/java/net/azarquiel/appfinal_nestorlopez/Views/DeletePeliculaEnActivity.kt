package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_delete_pelicula_en.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class DeletePeliculaEnActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pelicula_en)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnDeleteEnCategorias.setOnClickListener {
            //Launch activity delete pelicula en categorias
            val intent = Intent(this, DeletePeliculaEnCategoriasActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnDeleteEnCines.setOnClickListener {
            //Launch activity delete pelicula en cines
            val intent = Intent(this, DeletePeliculaEnCinesActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }
    }
}
