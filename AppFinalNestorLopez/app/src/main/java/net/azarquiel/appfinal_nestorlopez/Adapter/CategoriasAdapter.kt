package net.azarquiel.appfinal_nestorlopez.Adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.azarquiel.appfinal_nestorlopez.Class.Categoria
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.R

class CategoriasAdapter(private val context: Context,
                        private val listaCategorias:List<Categoria>?): RecyclerView.Adapter<CategoriasAdapter.MyViewHolder>() {

    private var dataList: List<Pelicula> = emptyList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_categoria,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaCategorias?.size ?:0
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.itemTitle.text = listaCategorias!![position].nombre

        if(listaCategorias[position].listaPeliculas != null) {

            var items = listaCategorias[position].listaPeliculas

            val itemListAdapter = PeliculasAdapter(context, items!!.sortedBy { it.titulo }.reversed())

            myViewHolder.recycler_view_list.setHasFixedSize(true)
            myViewHolder.recycler_view_list.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            myViewHolder.recycler_view_list.adapter = itemListAdapter

            myViewHolder.recycler_view_list.isNestedScrollingEnabled = false //Important
        }
    }

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        var itemTitle:TextView
        var recycler_view_list:RecyclerView

        init{
            itemTitle = view.findViewById(R.id.itemTitle) as TextView
            recycler_view_list = view.findViewById(R.id.recycler_view_list) as RecyclerView
        }

    }

    internal fun setPeliculas(peliculas: List<Pelicula>) {
        this.dataList = peliculas
        notifyDataSetChanged()
    }

}