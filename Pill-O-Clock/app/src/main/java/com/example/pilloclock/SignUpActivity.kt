package com.example.pilloclock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


@Suppress("NAME_SHADOWING")
class SignUpActivity : AppCompatActivity() {
    //Initiate firebase
    private lateinit var mAuth: FirebaseAuth

    //Reference to DB
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_1)

        mAuth = FirebaseAuth.getInstance()
        val btnCreateClick = findViewById<Button>(R.id.btnLogin)
        btnCreateClick.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = findViewById<EditText>(R.id.email).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        val firstname = findViewById<EditText>(R.id.FirstName).text.toString()
        val lastname = findViewById<EditText>(R.id.LastName).text.toString()
        val gender = findViewById<EditText>(R.id.gender).text.toString()
        val age = findViewById<EditText>(R.id.age).text.toString()

        if (email.isBlank()) {
            Toast.makeText(this@SignUpActivity, "Email cannot be blank.", Toast.LENGTH_SHORT).show()
        } else if (password.isBlank()) {
            Toast.makeText(this@SignUpActivity, "Password cannot be blank", Toast.LENGTH_SHORT)
                .show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserId = mAuth.currentUser!!.uid
                    refUsers =
                        FirebaseDatabase.getInstance().reference.child("User").child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["email"] = email
                    userHashMap["password"] = password
                    userHashMap["firstname"] = firstname
                    userHashMap["lastname"] = lastname
                    userHashMap["gender"] = gender
                    userHashMap["age"] = age

                    refUsers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                            val intent =
                                Intent(this@SignUpActivity, LandingPageActivity::class.java)
                            //not forcing login if user presses back button
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            // send to MainActivity page
                            startActivity(intent)
                        }
                        else {
                            var snackbar = Snackbar.make(currentFocus!!, task.exception!!.message.toString(), Snackbar.LENGTH_LONG)
                            snackbar.show()
                            //Toast.makeText( this@SignUpActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT ).show()
                        }
                    }
                }
                else{
                    var snackbar = Snackbar.make(currentFocus!!, task.exception!!.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Log In?",
                        View.OnClickListener {
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                        })
                    snackbar.show()
                }
            }
        }
    }
}
