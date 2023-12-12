package net.azarquiel.appfinal_nestorlopez.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import net.azarquiel.appfinal_nestorlopez.Class.Cine
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.Interface.ItemClickListener
import net.azarquiel.appfinal_nestorlopez.R
import net.azarquiel.appfinal_nestorlopez.Views.DetailCinesActivity

class CinesAdapter(private val context: Context,
                       private val listaCines:List<Cine>?) : RecyclerView.Adapter<CinesAdapter.ViewHolderProvincias>() {

    private var dataList: List<Pelicula> = emptyList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderProvincias {
        val view = LayoutInflater.from(context).inflate(R.layout.rowprovincia,p0,false)
        Log.d("P0","PO: $p0")
        Log.d("P0","P1: $p1")

        return ViewHolderProvincias(view)
    }

    override fun getItemCount(): Int {
        return listaCines?.size ?: 0
    }

    override fun onBindViewHolder(myViewHolderProvincias:ViewHolderProvincias, position: Int) {
        myViewHolderProvincias.txtTitle.setText(listaCines!![position].nombre!!)

        myViewHolderProvincias.setClick(object : ItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(context,""+listaCines[position].nombre, Toast.LENGTH_SHORT).show()

                val cine = Cine(
                    listaCines[position].cprovincia,
                    listaCines[position].ccine,
                    listaCines[position].nombre,
                    listaCines[position].direccion,
                    listaCines[position].latitud,
                    listaCines[position].longitud,
                    listaCines[position].listaPeliculas
                )
                Log.d("CINE","cine: $cine")

                val intent = Intent(context, DetailCinesActivity::class.java)
                intent.putExtra("cine",cine)
                ContextCompat.startActivity(context, intent, Bundle.EMPTY)
                Log.d("CINE","Enviado cine")
            }
        })
    }

    inner class ViewHolderProvincias(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var txtTitle: TextView
        var img_peli: ImageView

        lateinit var itemClickListener: ItemClickListener

        fun setClick(itemClickListener: ItemClickListener){
            this.itemClickListener = itemClickListener
        }

        init {
            txtTitle = view.findViewById(R.id.tvNombreCine)
            img_peli = view.findViewById(R.id.imgIconoCine)

            view.setOnClickListener(this)
        }

        override fun onClick(view: View?){
            itemClickListener.onItemClickListener(view!!,adapterPosition)

        }
    }

    internal fun setPeliculas(peliculas: List<Pelicula>) {
        this.dataList = peliculas
        notifyDataSetChanged()
    }


}
