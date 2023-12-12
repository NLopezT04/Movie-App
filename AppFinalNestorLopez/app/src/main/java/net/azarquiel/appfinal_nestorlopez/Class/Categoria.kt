package net.azarquiel.appfinal_nestorlopez.Class

import java.io.Serializable

data class Categoria(var cid:Int?=null, var nombre:String?=null
                     , var listaPeliculas:ArrayList<Pelicula>?=null):Serializable

