package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_cine.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class DeleteCineActivity : AppCompatActivity() {

    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_cine)
        usuarioActual = intent.getSerializableExtra("usuarioActual") as User

        btnDeleteCine.setOnClickListener {
            removeCineToFirebaseDatabase()
        }
        imgHome.setOnClickListener {
            //Volver a la DecisionActivity
            val intent = Intent(this,DecisionActivity::class.java)
            intent.putExtra("userActual",usuarioActual)
            startActivity(intent)
        }
    }

    private fun removeCineToFirebaseDatabase() {
        val cprovincia = tvDeleteIdProvinciaCine.text.toString().toInt()
        val ccine = tvDeleteIdCine.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("provincias/$cprovincia/listaCines").child(ccine)

        ref.removeValue()
        Toast.makeText(this,"Cine eliminado correctamente", Toast.LENGTH_SHORT).show()
    }
}
