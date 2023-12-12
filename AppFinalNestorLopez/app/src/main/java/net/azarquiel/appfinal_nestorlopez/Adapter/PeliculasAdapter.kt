package net.azarquiel.appfinal_nestorlopez.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.Interface.ItemClickListener
import net.azarquiel.appfinal_nestorlopez.R
import net.azarquiel.appfinal_nestorlopez.Views.DetailPeliculasActivity

class PeliculasAdapter(private val context: Context,
                               private val listaPeliculas:List<Pelicula>?) : RecyclerView.Adapter<PeliculasAdapter.ViewHolder>() {

    private var dataList: List<Pelicula> = emptyList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rowcategoria,p0,false)
        Log.d("P0","PO: $p0")
        Log.d("P0","P1: $p1")

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaPeliculas?.size ?: 0
    }

    internal fun setPeliculas(peliculas: List<Pelicula>) {
        this.dataList = peliculas
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(myViewHolder:ViewHolder, position: Int) {
        myViewHolder.txtTitle.setText(listaPeliculas!![position].titulo!!)

        Picasso.get().load(listaPeliculas[position].img).into(myViewHolder.img_peli)

        myViewHolder.setClick(object : ItemClickListener{
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(context,""+listaPeliculas[position].titulo,Toast.LENGTH_SHORT).show()

                val pelicula = Pelicula(
                    listaPeliculas[position].codigocategoria,
                    listaPeliculas[position].cpeli,
                    listaPeliculas[position].titulo,
                    listaPeliculas[position].genero,
                    listaPeliculas[position].duracion,
                    listaPeliculas[position].sinopsis,
                    listaPeliculas[position].actores,
                    listaPeliculas[position].img,
                    listaPeliculas[position].valoracion,
                    listaPeliculas[position].idProvinciaPelicula,
                    listaPeliculas[position].idCinePelicula,
                    listaPeliculas[position].idPeliculaEnCine
                )
                Log.d("PELICULA","pelicula: $pelicula")

                val intent = Intent(context,DetailPeliculasActivity::class.java)
                intent.putExtra("pelicula",pelicula)
                startActivity(context,intent, Bundle.EMPTY)
                Log.d("PELICULA","Enviada pelicula")
            }
        })
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var txtTitle:TextView
        var img_peli:ImageView

        lateinit var itemClickListener:ItemClickListener

        fun setClick(itemClickListener: ItemClickListener){
            this.itemClickListener = itemClickListener;
        }

        init {
            txtTitle = view.findViewById(R.id.tvNombrePelicula)
            img_peli = view.findViewById(R.id.imgIconoPelicula)

            view.setOnClickListener(this)
        }

        override fun onClick(view:View?){
            itemClickListener.onItemClickListener(view!!,adapterPosition)

        }
    }

}
