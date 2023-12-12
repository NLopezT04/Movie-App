package net.azarquiel.appfinal_nestorlopez.Class

import java.io.Serializable

data class Pelicula(var codigocategoria:Int?=null, var cpeli:Int?=null, var titulo:String?=null, var genero:String?=null, var duracion:Int?=null
                    , var sinopsis:String?=null, var actores:String?=null, var img:String?=null, var valoracion:Float?=null
                    , var idProvinciaPelicula:Int?=null, var idCinePelicula:Int?=null, var idPeliculaEnCine:Int?=null
):Serializable