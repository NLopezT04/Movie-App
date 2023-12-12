package net.azarquiel.appfinal_nestorlopez.Views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_newpelicula.*
import net.azarquiel.appfinal_nestorlopez.Class.Pelicula
import net.azarquiel.appfinal_nestorlopez.R
import java.util.*

class NewPeliculaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpelicula)

        btnImgNewPelicula.setOnClickListener {
            Log.d("BtnPhoto", "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

        btnAñadirNewPelicula.setOnClickListener {
            addPelicula()
        }

    }

    var PhotoPeliculaSelectedUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode ==  Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was...
            Log.d("RegisterPhoto","Photo was selected")

            PhotoPeliculaSelectedUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, PhotoPeliculaSelectedUri)
            Log.d("IMAGEN","PhotoUri: $PhotoPeliculaSelectedUri")
            uploadImageToFirebaseStorage()

            selectImgPelicula.setImageBitmap(bitmap)
            btnImgNewPelicula.alpha = 0f
        }
    }

    private fun addPelicula(){
        uploadImageToFirebaseStorage()
    }

    private fun uploadImageToFirebaseStorage(){
        if(PhotoPeliculaSelectedUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/peliculas/$filename")

        ref.putFile(PhotoPeliculaSelectedUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity","File location: $it")

                    savePeliculaToFirebaseDatabase(it.toString())
                }
            }
    }

    private fun savePeliculaToFirebaseDatabase(profileImageUrl:String){
        val ccategoria = tvNewIdCategoriaPelicula.text.toString().toInt()
        val cpeli = tvNewIdPelicula.text.toString()
        val cpeliEnCine = tvNewIdPeliculaEnElCine.text.toString()
        val cprov = tvNewIdProvinciaPelicula.text.toString().toInt()
        val ccine = tvNewIdCinePelicula.text.toString().toInt()
        val ref = FirebaseDatabase.getInstance().getReference("/categorias/$ccategoria/listaPeliculas/")
        val ref2 = FirebaseDatabase.getInstance().getReference("/provincias/$cprov/listaCines/$ccine/listaPeliculas/")
        val rating = 0.0f

        val pelicula = Pelicula(
            ccategoria,
            cpeli.toInt(),
            tvNewTituloPelicula.text.toString(),
            tvNewGeneroPelicula.text.toString(),
            tvNewDuracionPelicula.text.toString().toInt(),
            tvNewSinopsisPelicula.text.toString(),
            tvNewRepartoPelicula.text.toString(),
            profileImageUrl, rating,
            cprov, ccine, cpeliEnCine.toInt()
        )

        ref.child(cpeli).setValue(pelicula)
            .addOnSuccessListener {
                Log.d("NewPeliculaActivity","Finally we saved the movie to Firebase Database")

                Toast.makeText(this,"Pelicula correctamente añadida", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener {
                Log.d("NewPeliculaActivity","Failed to set value to database: ${it.message}")
            }

        ref2.child(cpeliEnCine).setValue(pelicula)
            .addOnSuccessListener {
                Log.d("NewPeliculaActivity","Finally we saved the movie to Firebase Database")
            }

            .addOnFailureListener {
                Log.d("NewPeliculaActivity","Failed to set value to database: ${it.message}")
            }
    }

}
