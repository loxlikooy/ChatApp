package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var txt_name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnsignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.Email)
        password = findViewById(R.id.password)
        txt_name = findViewById(R.id.txt_name)
        btnsignup = findViewById(R.id.btn_signup)

        btnsignup.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val name = txt_name.text.toString()
            signup(name, email, password)
        }
    }

    private fun signup(name:String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some error occured", Toast.LENGTH_SHORT).show()
                }

            }

    }
 private fun addUserToDatabase(name: String, email: String, uid: String)
 {
     mDbRef = FirebaseDatabase.getInstance().reference

     mDbRef.child("user").child(uid).setValue(User(name, email, uid))
 }

}