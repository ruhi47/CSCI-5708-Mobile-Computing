package com.example.pilloclock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class LoginActivity : AppCompatActivity() {
    //Initiate firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        val btnCreateClick = findViewById<Button>(R.id.btnLogin)
        btnCreateClick.setOnClickListener {
            logUser()
        }
    }

    private fun logUser() {
        val email = findViewById<EditText>(R.id.email_login).text.toString()
        val password =findViewById<EditText>(R.id.password_login).text.toString()

        if (email.isBlank()){
            Toast.makeText(this@LoginActivity, "Password cannot be blank", Toast.LENGTH_SHORT).show()
        }
        else if (password.isBlank()){
            Toast.makeText(this@LoginActivity, "Password cannot be blank", Toast.LENGTH_SHORT).show()
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val firebaseUserId = mAuth.currentUser!!.uid
                //change activity from MainActivity to DashboardActivity
                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                //not forcing login if user presses back button
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                // send to Dashboard
                startActivity(intent)
            }
            else {
                var snackbar = Snackbar.make(currentFocus!!, task.exception!!.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Sign up?",
                    View.OnClickListener {
                        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                        startActivity(intent)
                })
                snackbar.show()
                //Toast.makeText(this@LoginActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
