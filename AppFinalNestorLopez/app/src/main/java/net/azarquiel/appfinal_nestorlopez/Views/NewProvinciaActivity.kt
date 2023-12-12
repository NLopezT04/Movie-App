package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_newprovincia.*
import net.azarquiel.appfinal_nestorlopez.Class.Provincia
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class NewProvinciaActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newprovincia)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnAñadirProvincia.setOnClickListener {
            saveProvinciaToFirebaseDatabase()
            limpiar()
        }
        imgHome.setOnClickListener {
            //Volver a la DecisionActivity
            val intent = Intent(this,DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }
    }

    private fun saveProvinciaToFirebaseDatabase() {
        val cprov = tvNewIdProvincia.text.toString().toInt()
        val ref = FirebaseDatabase.getInstance().getReference("/provincias/")

        val provincia = Provincia(
            cprov,
            tvNewNombreProvincia.text.toString()
        )

        ref.child(cprov.toString()).setValue(provincia)
            .addOnSuccessListener {
                Log.d("NewProvinciaActivity","Finally we saved provincia to Firebase Database")

                Toast.makeText(this,"Provincia correctamente añadida", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener {
                Log.d("NewProvinciaActivity","Failed to set value to database: ${it.message}")
            }
    }

    private fun limpiar(){
        tvNewIdProvincia.text.clear()
        tvNewNombreProvincia.text.clear()
    }
}
