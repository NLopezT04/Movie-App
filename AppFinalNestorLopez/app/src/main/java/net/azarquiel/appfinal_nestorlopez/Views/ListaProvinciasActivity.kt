package net.azarquiel.appfinal_nestorlopez.Views

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_lista_provincias.*
import net.azarquiel.appfinal_nestorlopez.Adapter.ProvinciasAdapter
import net.azarquiel.appfinal_nestorlopez.Class.Cine
import net.azarquiel.appfinal_nestorlopez.Class.Provincia
import net.azarquiel.appfinal_nestorlopez.Interface.FirebaseLoadListenerProvincias
import net.azarquiel.appfinal_nestorlopez.R

class ListaProvinciasActivity : AppCompatActivity(), FirebaseLoadListenerProvincias {

    lateinit var dialog: AlertDialog
    lateinit var iFirebaseLoadListenerProvincias: FirebaseLoadListenerProvincias
    lateinit var myData: DatabaseReference

    override fun onFirebaseLoadFailed(message: String) {
        dialog.dismiss()
        Toast.makeText(this@ListaProvinciasActivity,message, Toast.LENGTH_SHORT).show()
    }

    override fun onFirebaseLoadSuccessProvincias(provincias: List<Provincia>) {
        val adapter = ProvinciasAdapter(this@ListaProvinciasActivity,provincias)
        my_recycler_view_provincias.adapter = adapter
        dialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_provincias)

        //Init
        dialog = SpotsDialog.Builder().setContext(this).build()
        myData = FirebaseDatabase.getInstance().getReference("/provincias")
        iFirebaseLoadListenerProvincias = this

        //View
        my_recycler_view_provincias.setHasFixedSize(true)
        my_recycler_view_provincias.layoutManager = LinearLayoutManager(this)

        getFirebaseData()
    }

    private fun getFirebaseData() {

        dialog.show()

        myData.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                iFirebaseLoadListenerProvincias.onFirebaseLoadFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val itemProvincias = ArrayList<Provincia>()
                for(myDataSnapShot in p0.children){
                    val itemProvincia = Provincia()
                    itemProvincia.nombre = myDataSnapShot.child("nombre")
                        .getValue(true).toString()
                    val t = object: GenericTypeIndicator<ArrayList<Cine>>(){}
                    itemProvincia.listaCines = myDataSnapShot.child("listaCines").getValue(t)
                    itemProvincias.add(itemProvincia)
                }
                iFirebaseLoadListenerProvincias.onFirebaseLoadSuccessProvincias(itemProvincias)
            }
        })
    }
}
