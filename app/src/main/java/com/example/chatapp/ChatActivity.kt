package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var  messageBox: EditText
    private lateinit var  sendButton: ImageView
    private lateinit var  messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Messagelist>
    private lateinit var mDbref: DatabaseReference
    var receiverRoom: String?= null
    var senderRoom: String?= null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val intent = Intent()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbref = FirebaseDatabase.getInstance().getReference()
        senderRoom = receiverUid+senderUid
        receiverRoom= senderUid+receiverUid
        supportActionBar?.title = name

        messageRecyclerView = findViewById(R.id.chatResycler)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentbutton)
        messageList= ArrayList()
        messageAdapter =  MessageAdapter(this,messageList)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter






        sendButton.setOnClickListener{
            val message = messageBox.text.toString()
            val messagelistObject = Messagelist(message, receiverUid)

            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messagelistObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messagelistObject)

                }
                    messageBox.setText("")
        }

        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Messagelist::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}