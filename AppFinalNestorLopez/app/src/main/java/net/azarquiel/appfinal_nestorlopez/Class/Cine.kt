package net.azarquiel.appfinal_nestorlopez.Class

import java.io.Serializable

data class Cine (var cprovincia:Int?=null, var ccine:Int?=null, var nombre:String?=null,
                 var direccion:String?=null, var latitud:Double?=null, var longitud:Double?=null,
                 var listaPeliculas:ArrayList<Pelicula>?=null):Serializable