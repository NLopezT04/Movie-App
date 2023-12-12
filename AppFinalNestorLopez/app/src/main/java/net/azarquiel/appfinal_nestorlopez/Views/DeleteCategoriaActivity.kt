package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_categoria.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class DeleteCategoriaActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_categoria)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnDeleteCategoria.setOnClickListener {
            removeCategoriaToFirebaseDatabase()
            limpiar()
        }

        imgHome.setOnClickListener {
            //viajar a la home
            val intent = Intent(this,DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }

    }

    private fun removeCategoriaToFirebaseDatabase(){
        val cid = tvDeleteIdCategoria.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("categorias").child(cid)

        ref.removeValue()
        Toast.makeText(this,"Categoria eliminada correctamente",Toast.LENGTH_SHORT).show()
    }

    private fun limpiar(){
        tvDeleteIdCategoria.text.clear()
    }
}
