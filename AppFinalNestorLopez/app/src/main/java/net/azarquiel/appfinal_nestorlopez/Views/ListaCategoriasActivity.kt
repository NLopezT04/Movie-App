package net.azarquiel.appfinal_nestorlopez.Views

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_lista_categorias.*
import net.azarquiel.appfinal_nestorlopez.Adapter.CategoriasAdapter
import net.azarquiel.appfinal_nestorlopez.Class.Categoria
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.Interface.FirebaseLoadListenerCategorias
import net.azarquiel.appfinal_nestorlopez.R

class ListaCategoriasActivity : AppCompatActivity(), FirebaseLoadListenerCategorias {

    lateinit var dialog: AlertDialog
    private lateinit var adapter: CategoriasAdapter
    lateinit var iFirebaseLoadListenerCategorias: FirebaseLoadListenerCategorias
    lateinit var myData:DatabaseReference

    override fun onFirebaseLoadFailed(message: String) {
            dialog.dismiss()
            Toast.makeText(this@ListaCategoriasActivity,message,Toast.LENGTH_SHORT).show()
        }

        override fun onFirebaseLoadSuccess(categorias: List<Categoria>) {
            adapter = CategoriasAdapter(this@ListaCategoriasActivity,categorias)
            my_recycler_view.adapter = adapter
            dialog.dismiss()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_lista_categorias)

            //Init
            dialog = SpotsDialog.Builder().setContext(this).build()
            myData = FirebaseDatabase.getInstance().getReference("/categorias")
            iFirebaseLoadListenerCategorias = this

            //View
            my_recycler_view.setHasFixedSize(true)
            my_recycler_view.layoutManager = LinearLayoutManager(this)

            getFirebaseData()
        }

    //Obtener categorias con ArrayList de Peliculas
    private fun getFirebaseData() {

            dialog.show()

            myData.addListenerForSingleValueEvent(object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                    iFirebaseLoadListenerCategorias.onFirebaseLoadFailed(p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val itemCategorias = ArrayList<Categoria>()
                    for(myDataSnapShot in p0.children){
                        val itemCategoria = Categoria()
                        itemCategoria.nombre = myDataSnapShot.child("nombre")
                            .getValue(true).toString()
                        Log.d("ITEMCATEGORIA","ITEM CATEGORIA 1: $itemCategoria")
                        val t = object:GenericTypeIndicator<ArrayList<Pelicula>>(){}
                        itemCategoria.listaPeliculas = myDataSnapShot.child("listaPeliculas").getValue(t)
                        itemCategorias.add(itemCategoria)
                        Log.d("ITEMCATEGORIA","ITEM CATEGORIA 2: $itemCategoria")
                    }
                    iFirebaseLoadListenerCategorias.onFirebaseLoadSuccess(itemCategorias)
                }
            })
        }
}
