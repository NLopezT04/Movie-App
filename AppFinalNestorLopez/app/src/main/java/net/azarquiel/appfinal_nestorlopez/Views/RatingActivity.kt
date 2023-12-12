package net.azarquiel.appfinal_nestorlopez.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_rating.*
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.R
import org.jetbrains.anko.toast

class RatingActivity : AppCompatActivity() {

    lateinit var ratingBar : RatingBar
    lateinit var pelicula : Pelicula
    lateinit var botonValorar : Button
    var divisor : Int = 0
    var contador : Int = 1
    var resultado : Float = 0.0f
    var historico : Float = 0.0f
    var total : Float = 0.0f
    var valoracion : Float = 0.0f
    var ratingValue : Float = 2f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        ratingBar = findViewById(R.id.ratingBarPelicula) as RatingBar
        botonValorar = findViewById(R.id.btnValorarPelicula) as Button

        pelicula = intent.getSerializableExtra("pelicula") as Pelicula
        Log.d("PELICULA","Pelicula Recogida $pelicula")

        accederABBDD()

        btnValorarPelicula.setOnClickListener {
            calcularMedia(valoracion)
        }

    }

    private fun accederABBDD(){
        val ccategoria = pelicula.codigocategoria
        val cpeli = pelicula.cpeli
        val ref = FirebaseDatabase.getInstance().getReference("/categorias/$ccategoria/listaPeliculas/$cpeli/")

        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot){
                p0.children.mapNotNull {
                    val media = p0.getValue<Pelicula>(Pelicula::class.java)
                    valoracion = media!!.valoracion!!
                    Log.d("VALORACION","$valoracion")
                }
            }
        })
    }

    //Calcular media y guardarla en Firebasse
    private fun calcularMedia(valoracion: Float): Int {
        ratingValue = ratingBar.rating
        Log.d("CONTADOR","ratingValue: $ratingValue")
        var valorInt = valoracion.toInt()
        var valorFloat = valoracion
        Log.d("CONTADOR","ratingValue: $ratingValue")
        Log.d("CONTADOR","ratingValue: $ratingValue")
            if(valorInt == 0){
                divisor = 0
            }else{
                divisor = 1
            }
        resultado = ratingValue + valorFloat
        Log.d("VALORAR","resultado $resultado")
        historico += resultado
        Log.d("VALORAR","historico $historico")
        divisor++
        Log.d("VALORAR","divisor $divisor")
        total = (historico / divisor)/contador
        Log.d("VALORAR","contador $contador")
        Log.d("VALORAR","total $total")
        contador++
        toast("Valoración de $ratingValue guardada")

        val ccategoria = pelicula.codigocategoria
        val cpeli = pelicula.cpeli
        val cpeliEnCine = pelicula.idPeliculaEnCine
        val cprov = pelicula.idProvinciaPelicula
        val ccine = pelicula.idCinePelicula
        val refVal = FirebaseDatabase.getInstance()
            .getReference("/categorias/$ccategoria/listaPeliculas/$cpeli/valoracion/")
        val ref2Val = FirebaseDatabase.getInstance()
            .getReference("/provincias/$cprov/listaCines/$ccine/listaPeliculas/$cpeliEnCine/valoracion/")

        refVal.setValue(total)
        ref2Val.setValue(total)
        return contador
    }
}
