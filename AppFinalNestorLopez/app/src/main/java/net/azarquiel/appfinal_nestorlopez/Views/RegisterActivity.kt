package net.azarquiel.appfinal_nestorlopez.Views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            register()
        }

        tvLogin.setOnClickListener {
            Log.d("Register","Try to show login activity")

            //launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSelectFoto.setOnClickListener {
            Log.d("BtnPhoto", "Try to show photo selector")

            //launch galeria de fotos
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

    }

    var selectPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode ==  Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was...
            Log.d("RegisterPhoto","Photo was selected")

            selectPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoUri)

            selectphoto.setImageBitmap(bitmap)
            btnSelectFoto.alpha = 0f
        }
    }

    //Registrar
    private fun register(){

        email = tvRegisterEmail.text.toString()
        password = tvRegisterPassword.text.toString()
        username = tvRegisterNombre.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this,"Por favor rellena la casilla Email", Toast.LENGTH_SHORT).show()
            return
        }
        if(username.isEmpty()){
            Toast.makeText(this,"Por favor rellena la casilla Username", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isEmpty()){
            Toast.makeText(this,"Por favor rellena la casilla Password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("Register", "Email is: " + email)
        Log.d("Register", "Password: $password")

        //Firebase Authentication, create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful)
                    return@addOnCompleteListener

                Log.d("RegisterActivity","Successfully created user with uid: ${it.result!!.user.uid}")
                uploadImageToFirebaseStorage()

            }.addOnFailureListener{
                Log.d("RegisterActivity","Failed to create user: ${it.message}")
            }
    }

    //Subir Img a la Storage de Firebase
    private fun uploadImageToFirebaseStorage(){
        if(selectPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity","File location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
    }

    //Subir el Usuario a la Database de Firebase
    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid= FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(
            uid,
            tvRegisterNombre.text.toString(),
            tvRegisterEmail.text.toString(),
            tvRegisterPassword.text.toString(),
            profileImageUrl
        )
        Log.d("IMAGEN","IMAGE: $profileImageUrl")

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
                val intent = Intent(this,DecisionActivity::class.java)
                intent.putExtra("userActual",user)
                startActivity(intent)
                Log.d("USER","Enviado usuario")

                Toast.makeText(this,"Correctamente Registrado",Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener {
                Log.d("RegisterActivity","Failed to set value to database: ${it.message}")
            }


    }
}
