package net.azarquiel.appfinal_nestorlopez.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_newcine.*
import net.azarquiel.appfinal_nestorlopez.Class.Cine
import net.azarquiel.appfinal_nestorlopez.R

class NewCineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newcine)

        btnAñadirCine.setOnClickListener {
            saveCineToFirebaseDatabase()
            limpiar()
        }
    }

    private fun saveCineToFirebaseDatabase() {
        val cprovincia = tvNewIdProvinciaCine.text.toString().toInt()
        val ccine = tvNewIdCine.text.toString().toInt()
        val ref = FirebaseDatabase.getInstance().getReference("/provincias/$cprovincia/listaCines/")

        val cine = Cine(
            cprovincia,
            ccine,
            tvNewNombreCine.text.toString(),
            tvNewDireccionCine.text.toString(),
            tvNewLatitudCine.text.toString().toDouble(),
            tvNewLongitudCine.text.toString().toDouble()
        )

        ref.child(ccine.toString()).setValue(cine)
            .addOnSuccessListener {
                Log.d("NewCategoriaActivity","Finally we saved the category to Firebase Database")

                Toast.makeText(this,"Cine correctamente añadido", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener {
                Log.d("NewCategoriaActivity","Failed to set value to database: ${it.message}")
            }
    }

    private fun limpiar(){
        tvNewIdProvinciaCine.text.clear()
        tvNewIdCine.text.clear()
        tvNewNombreCine.text.clear()
        tvNewDireccionCine.text.clear()
        tvNewLatitudCine.text.clear()
        tvNewLongitudCine.text.clear()
    }

}
