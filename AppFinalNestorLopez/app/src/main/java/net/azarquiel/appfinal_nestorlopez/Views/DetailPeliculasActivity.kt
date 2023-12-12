package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_peliculas.*
import kotlinx.android.synthetic.main.content_detail_peliculas.*
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.R
import org.jetbrains.anko.*

class DetailPeliculasActivity : AppCompatActivity() {

    private lateinit var pelicula:Pelicula

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_detail_peliculas)

        pelicula = intent.getSerializableExtra("pelicula") as Pelicula
        Log.d("PELICULA","Pelicula Recogida $pelicula")

        pintar()
        fab.setOnClickListener { view ->
            mostrarFoto()
        }

        btnValorar.setOnClickListener {
            //Valorar pelicula
            val intent = Intent(this,RatingActivity::class.java)
            intent.putExtra("pelicula", pelicula)
            startActivity(intent)
        }

    }

    private fun pintar(){
        tvTitulo.text = pelicula.titulo
        tvGenero.text = pelicula.genero
        tvSinopsis.text = pelicula.sinopsis
        tvReparto.text = pelicula.actores
        tvDuracion.text = pelicula.duracion.toString()
        tvRating.text = pelicula.valoracion.toString()
    }

    private fun mostrarFoto(){
        alert {
            title = "CARTELERA"
                customView{
                verticalLayout {
                    lparams(width = wrapContent, height = wrapContent)
                    val imgAlert = imageView {
                        padding = dip(8)
                    }
                    Picasso.get().load(pelicula.img).into(imgAlert)
                }
            }
        }.show()
    }
}
