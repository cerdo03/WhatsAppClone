 package com.example.whatsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.Adapter.ChatAdapter
import com.example.whatsapp.Models.MessageModel
import com.example.whatsapp.Models.Users
import com.example.whatsapp.databinding.ActivityChatDetailBinding
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

 class ChatDetailActivity : AppCompatActivity() {
     lateinit var binding: ActivityChatDetailBinding
     lateinit var database:FirebaseDatabase
     lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val senderId = auth.uid
        val recieverId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")
        val profilePic = intent.getStringExtra("profilePic")
        binding.userName.text = userName
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar3).into(binding.profileImage)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val msgModels=ArrayList<MessageModel>()
        val chatAdapter = ChatAdapter(this,msgModels,recieverId)
        binding.chatRecyclerView.adapter=chatAdapter

        val manager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager = manager
        val senderRoom = senderId + recieverId
        val recieverRoom = recieverId+senderId

        database.reference.child("chats").child(senderRoom).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    msgModels.clear()
                    for(i  in dataSnapshot.children){
                        val model = i.getValue(MessageModel::class.java)
                        model?.messageId = i.key
                        if (model != null) {
                            msgModels.add(model)
                        }


                    }
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                }
            }
        )


        binding.send.setOnClickListener {
            val msg = binding.enterMessage.text.toString()
            val msgModel = MessageModel(senderId,msg)
            msgModel.timeStamp = Date().time
            binding.enterMessage.setText("")
            database.reference.child("chats")
                .child(senderRoom)
                .push()
                .setValue(msgModel)
                .addOnSuccessListener {
                    database.reference.child("chats").child(recieverRoom).push()
                        .setValue(msgModel)
                        .addOnSuccessListener {
                            database.reference.child("chats").child(senderRoom).push()
                                .setValue(msgModel).addOnSuccessListener {

                                }
                        }
                }

        }

    }
}