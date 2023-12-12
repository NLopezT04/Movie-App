package net.azarquiel.appfinal_nestorlopez.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import net.azarquiel.appfinal_nestorlopez.Class.User
import net.azarquiel.appfinal_nestorlopez.R

class LoginActivity :AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            logear()
        }
    }

    //Comprobar que los estan los campos llenos
    private fun logear(){
        auth = FirebaseAuth.getInstance()
        val email = tvLoginEmail.text.toString()
        val password = tvLoginPassword.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this,"Por favor rellena la casilla Email", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isEmpty()){
            Toast.makeText(this,"Por favor rellena la casilla Password", Toast.LENGTH_SHORT).show()
            return
        }

        login(email,password)
    }

    //Login with Authentication Firebase
    private fun login(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("LoginActivity", "Successfully login user")
                    Toast.makeText(this, "Logeado Correctamente", Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    updateUI(user)

                }else {
                    Log.d("LoginActivity", "Failed to create user")
                    Toast.makeText(this, "No tienes cuenta con esos datos, registrate por favor", Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }

    //Mandar el usuario logeado a la DecisionActivity
    private fun updateUI(user: FirebaseUser?) {
        Log.d("LoginActivity", "Updating UI")
        if(user != null) {
            val uidsuser = user.uid

            val ref = FirebaseDatabase.getInstance().getReference("/users/$uidsuser/")
            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.mapNotNull {
                        val img = p0.child("profileImageUrl").getValue(true).toString()
                        val username = p0.child("username").getValue(true).toString()

                        val user =
                            User(uidsuser, username, tvLoginEmail.text.toString(), tvLoginPassword.text.toString(), img)

                        val intent = Intent(this@LoginActivity, DecisionActivity::class.java)
                        intent.putExtra("userActual", user)
                        startActivity(intent)

                    }

                }
            })
        }
    }
}