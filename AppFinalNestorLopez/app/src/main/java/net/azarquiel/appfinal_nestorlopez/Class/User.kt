package net.azarquiel.appfinal_nestorlopez.Class

import java.io.Serializable

data class User(var uid:String?=null, var username:String, var email:String?=null, var password:String?=null,
                var profileImageUrl: String?=null) :Serializable{
}