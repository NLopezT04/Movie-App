package net.azarquiel.appfinal_nestorlopez.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowpeliculadisponibleen.view.*
import net.azarquiel.appfinal_nestorlopez.Class.Cine
import net.azarquiel.appfinal_nestorlopez.Class.ListaPeliculas
import net.azarquiel.appfinal_nestorlopez.R

class ListaPeliculasEnCineActivity : AppCompatActivity(){

    lateinit var cine : Cine
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelicula_disponible_en)

        cine = intent.getSerializableExtra("cineEntero") as Cine
        Log.d("CINE RECIBIDO", "$cine")
        val cprov = cine.cprovincia
        val ccine = cine.ccine

        mDatabase = FirebaseDatabase.getInstance().getReference("/provincias/$cprov/listaCines/$ccine").child("listaPeliculas")
        Log.d("PELICULAS","DATABASE: $mDatabase")

        mRecyclerView = findViewById(R.id.recyclerPeliculaDisponibleEn)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        logRecyclerView()
    }

    private fun logRecyclerView() {
        Log.d("AQUIIIIIII","AQUI LLEGA")
        var adapter = object : FirebaseRecyclerAdapter<ListaPeliculas,ListaPeliculasViewHolder>(
            ListaPeliculas::class.java,
            R.layout.rowpeliculadisponibleen,
            ListaPeliculasViewHolder::class.java,
            mDatabase

        ){
            override fun populateViewHolder(
                viewHolder: ListaPeliculasViewHolder?,
                model: ListaPeliculas,
                position: Int
            ) {
                Log.d("AQUIIIIIII","Hola")
                viewHolder!!.mView.tvNombrePeliculaDisponibleEn.text = model.titulo
                Log.d("PELICULAS","VIEWHOLDER: $viewHolder")
                Picasso.get().load(model.img).into(viewHolder.mView.imgIconoPeliculaDisponibleEn)
            }

        }
        Log.d("AQUIIIIIII","Hola2")
        mRecyclerView.adapter = adapter
    }

    class ListaPeliculasViewHolder(var mView: View) : RecyclerView.ViewHolder(mView)

}
