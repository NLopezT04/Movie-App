package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_pelicula_en_categorias.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class DeletePeliculaEnCategoriasActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pelicula_en_categorias)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnBorrarPeliculaCategoria.setOnClickListener {
            removePeliculaToFirebaseDatabase()
            limpiar()
        }

        imgHome.setOnClickListener {
            //Volver a la DecisionActivity
            val intent = Intent(this,DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }
    }

    private fun removePeliculaToFirebaseDatabase() {
        val ccategoria = tvDeleteIdCategoria.text.toString().toInt()
        val cpeli = tvDeleteIdPelicula.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("categorias/$ccategoria/listaPeliculas").child(cpeli)

        ref.removeValue()
        Toast.makeText(this,"Pelicula eliminada de categoria correctamente", Toast.LENGTH_SHORT).show()
    }

    private fun limpiar(){
        tvDeleteIdCategoria.text.clear()
        tvDeleteIdPelicula.text.clear()
    }

}
