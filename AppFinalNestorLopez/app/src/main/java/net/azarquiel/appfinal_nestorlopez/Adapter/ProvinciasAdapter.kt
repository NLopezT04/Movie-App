package net.azarquiel.appfinal_nestorlopez.Adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.azarquiel.appfinal_nestorlopez.Class.Provincia
import net.azarquiel.appfinal_nestorlopez.R

class ProvinciasAdapter(private val context: Context,
                        private val listaProvincias:List<Provincia>?): RecyclerView.Adapter<ProvinciasAdapter.ViewHolderProvincias>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderProvincias {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_provincia,p0,false)
        return ViewHolderProvincias(view)
    }

    override fun getItemCount(): Int {
        return listaProvincias?.size ?:0
    }

    override fun onBindViewHolder(myViewHolderProvincias: ViewHolderProvincias, position: Int) {
        myViewHolderProvincias.itemTitleProvincias.text = listaProvincias!![position].nombre

        if(listaProvincias[position].listaCines != null) {

            var items = listaProvincias[position].listaCines

            val itemListAdapter = CinesAdapter(context, items!!.sortedBy { it.nombre })

            myViewHolderProvincias.recycler_view_list_provincias.setHasFixedSize(true)
            myViewHolderProvincias.recycler_view_list_provincias.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            myViewHolderProvincias.recycler_view_list_provincias.adapter = itemListAdapter

            myViewHolderProvincias.recycler_view_list_provincias.isNestedScrollingEnabled = false //Important
        }
    }

    inner class ViewHolderProvincias(view: View):RecyclerView.ViewHolder(view){
        var itemTitleProvincias: TextView
        var recycler_view_list_provincias:RecyclerView

        init{
            itemTitleProvincias = view.findViewById(R.id.itemTitleProvincias) as TextView
            recycler_view_list_provincias = view.findViewById(R.id.recycler_view_list_provincias) as RecyclerView
        }

    }
}