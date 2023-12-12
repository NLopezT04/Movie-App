package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.azarquiel.appfinal_nestorlopez.R
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity(){

    private lateinit var reproductor : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //Musica pantalla de carga
        reproductor = MediaPlayer.create(this, R.raw.musicapp)
        reproductor.isLooping = true
        reproductor.start()

        //Cargando...
        val background = object : Thread(){
            override fun run(){
                try {
                    Thread.sleep(10000)
                    reproductor.stop()
                    val intent = Intent(baseContext, RegisterActivity::class.java)
                    startActivity(intent)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()

    }
}