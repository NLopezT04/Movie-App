package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_newcategoria.*
import net.azarquiel.appfinal_nestorlopez.Class.Categoria
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class NewCategoriaActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newcategoria)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnAñadirCosas.setOnClickListener {
            saveCategoriaToFirebaseDatabase()
            limpiar()
        }

        imgHome.setOnClickListener {
            //Volver a la DecisionActivity
            val intent = Intent(this,DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }
    }

    private fun saveCategoriaToFirebaseDatabase(){
        val cid = tvNewIdCategoria.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("/categorias/")

        val categoria = Categoria(
            cid.toInt(),
            tvNewNombreCategoria.text.toString()
        )

        ref.child(cid).setValue(categoria)
            .addOnSuccessListener {
                Log.d("NewCategoriaActivity","Finally we saved the category to Firebase Database")

                Toast.makeText(this,"Categoria correctamente añadida", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener {
                Log.d("NewCategoriaActivity","Failed to set value to database: ${it.message}")
            }
    }

    private fun limpiar(){
        tvNewIdCategoria.text.clear()
        tvNewNombreCategoria.text.clear()
    }

}
