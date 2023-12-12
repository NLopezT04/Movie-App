package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_decision.*
import kotlinx.android.synthetic.main.app_bar_decision.*
import kotlinx.android.synthetic.main.content_decision.*
import kotlinx.android.synthetic.main.nav_header_decision.view.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R
import org.jetbrains.anko.*

class DecisionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var NickUser: TextView
    private lateinit var CorreoUser: TextView
    private lateinit var ImgUser: ImageView
    private lateinit var usuarioActual: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decision)
        setSupportActionBar(toolbar)

        usuarioActual = intent.getSerializableExtra("userActual") as User
        Log.d("USUARIO","usuario recibido $usuarioActual")

        NickUser = nav_view.getHeaderView(0).tvNickUser
        CorreoUser = nav_view.getHeaderView(0).tvCorreoUser
        ImgUser = nav_view.getHeaderView(0).imgUser

        pintarUser()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnPeliculas.setOnClickListener {
            //Launch Peliculas Activity
            val intent = Intent(this, ListaCategoriasActivity::class.java)
            startActivity(intent)
        }

        btnCines.setOnClickListener {
            //Launch Cines Activity
            val intent = Intent(this, ListaProvinciasActivity::class.java)
            startActivity(intent)
        }

        btnAñadirCosas.setOnClickListener {
            //Launch activity add
            val intent = Intent(this, RootAñadirActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }

        btnDeleteCosas.setOnClickListener {
            //Launch activity delete
            val intent = Intent(this, RootDeleteActivity::class.java)
            intent.putExtra("usuarioActual",usuarioActual)
            startActivity(intent)
        }
    }

    private fun pintarUser(){
        NickUser.text = usuarioActual.username
        CorreoUser.text = usuarioActual.email
        Picasso.get().load(usuarioActual.profileImageUrl).into(ImgUser)

        if(usuarioActual.email == "nestoroot@gmail.com"){
            val btnAñadir = findViewById(R.id.btnAñadirCosas) as Button
            val btnBorrar = findViewById(R.id.btnDeleteCosas) as Button
            btnAñadir.visibility = View.VISIBLE
            btnBorrar.visibility = View.VISIBLE
        }else{

        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                Toast.makeText(this, "Has cerrado sesión correctamente", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
            R.id.nav_info ->{
                dialogInfo()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun dialogInfo() {
        alert("App hecha por Néstor López - 2019") {
            title = "INFO"
        }.show()
    }
}
