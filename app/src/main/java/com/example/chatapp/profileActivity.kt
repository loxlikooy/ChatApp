package com.example.chatapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class profileActivity : AppCompatActivity() {
    private lateinit var mDbRef: DatabaseReference
    private lateinit var uid: String

    private var name: String = "Test"

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mDbRef = FirebaseDatabase.getInstance().getReference()
        uid = FirebaseAuth.getInstance().currentUser?.uid!!
//        name = mDbRef.child("user").child(uid.toString()).child("txt_name").toString()
        mAuth=FirebaseAuth.getInstance()

        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    println("AAAAAAAAAAAA")
                    println(currentUser?.txt_name)
                    println(mAuth.currentUser?.uid)
                    println("AAAAAAAAAAAAAa")
                    if (mAuth.currentUser?.uid == currentUser?.uid) {
                        supportActionBar?.title = "Profile page - " + currentUser?.txt_name;
                        val pName: TextView = findViewById(R.id.profileName)
                        val pUid: TextView = findViewById(R.id.profileUid)
                        val pEmail: TextView = findViewById(R.id.profileEmail)
                        pName.text = currentUser?.txt_name
                        pUid.text = currentUser?.uid
                        pEmail.text = currentUser?.email
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}