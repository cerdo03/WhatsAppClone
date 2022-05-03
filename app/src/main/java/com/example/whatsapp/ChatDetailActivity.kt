 package com.example.whatsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsapp.databinding.ActivityChatDetailBinding
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

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
        val recieveId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")
        val profilePic = intent.getStringExtra("profilePic")
        binding.userName.text = userName
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar3).into(binding.profileImage)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}