package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_provincia.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class DeleteProvinciaActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_provincia)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnDeleteProvincia.setOnClickListener {
            removeProvinciaToFirebaseDatabase()
        }

        imgHome.setOnClickListener {
            //Volver a la DecisionActivity
            val intent = Intent(this, DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }
    }

        private fun removeProvinciaToFirebaseDatabase(){
            val cprov = tvDeleteIdProvinciaPelicula.text.toString()
            val ref = FirebaseDatabase.getInstance().getReference("provincias").child(cprov)

            ref.removeValue()
            Toast.makeText(this,"Provincia eliminada correctamente", Toast.LENGTH_SHORT).show()
        }
}
