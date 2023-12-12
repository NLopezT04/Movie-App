package net.azarquiel.appfinal_nestorlopez.Interface

import net.azarquiel.appfinal_nestorlopez.Class.Categoria

interface FirebaseLoadListenerCategorias {
    fun onFirebaseLoadSuccess(categorias:List<Categoria>)
    fun onFirebaseLoadFailed(message:String)
}