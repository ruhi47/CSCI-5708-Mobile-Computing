package com.example.pilloclock

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.entity.User
import com.example.pilloclock.data.repo.UserRepository
import com.example.pilloclock.databinding.ActivityEditUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_user_profile.*
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.HashMap

class EditUserProfile : AppCompatActivity() {
    var firstName: EditText? = null
    var lastName: EditText? = null
    var maleCheckbox: CheckBox? = null
    var femaleCheckbox: CheckBox? = null
    var otherCheckbox: CheckBox? = null
    var age: EditText? = null
    var user: HashMap<String, Any>? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)

        firstName = findViewById(R.id.fname_inputField)
        lastName = findViewById(R.id.lname_inputField)
        maleCheckbox = findViewById(R.id.male_checkbox)
        femaleCheckbox = findViewById(R.id.female_checkbox)
        otherCheckbox = findViewById(R.id.other_checkbox)
        age = findViewById(R.id.age_inputField)

        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth.currentUser!!.uid.toString()

        refUsers = FirebaseDatabase.getInstance().reference.child("User")
        getUserData(uid)
        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            val userHashMap = HashMap<String, Any>()
            userHashMap["uid"] = uid
            userHashMap["firstname"] = firstName!!.text.toString()
            userHashMap["lastname"] = lastName!!.text.toString()
            userHashMap["age"] = age_inputField!!.text.toString()
            if(maleCheckbox!!.isChecked) {
                userHashMap["gender"] = "Male"
            }
            else if(femaleCheckbox!!.isChecked) {
                userHashMap["gender"] = "Female"
            }
            else if(otherCheckbox!!.isChecked) {
                userHashMap["gender"] = "Other"
            }
            refUsers.child(uid).updateChildren(userHashMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DashboardActivity::class.java)
                    // send to MainActivity page
                    startActivity(intent)
                } else {
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserData(uid: String) {
        refUsers.child(uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.value as HashMap<String, Any>
                firstName!!.setText(user!!["firstname"].toString())
                lastName!!.setText(user!!["lastname"].toString())
                maleCheckbox!!.isChecked = user!!["gender"].toString() == "Male"
                femaleCheckbox!!.isChecked = user!!["gender"].toString() == "Female"
                otherCheckbox!!.isChecked = user!!["gender"].toString() == "Other"
                age!!.setText(user!!["age"].toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}



