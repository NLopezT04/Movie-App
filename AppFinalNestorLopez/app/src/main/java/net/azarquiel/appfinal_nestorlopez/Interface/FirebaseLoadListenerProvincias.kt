package net.azarquiel.appfinal_nestorlopez.Interface

import net.azarquiel.appfinal_nestorlopez.Class.Provincia

interface FirebaseLoadListenerProvincias {
    fun onFirebaseLoadSuccessProvincias(provincias:List<Provincia>)
    fun onFirebaseLoadFailed(message:String)
}