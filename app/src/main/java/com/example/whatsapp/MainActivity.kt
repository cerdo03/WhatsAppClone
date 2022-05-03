package com.example.whatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.whatsapp.Adapter.FragmentsAdapter
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.databinding.ActivitySignInBinding
import com.example.whatsapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth
         

        binding.viewPager.adapter = FragmentsAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settings){

        }else if(item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.groupChat){

        }
        return super.onOptionsItemSelected(item)
    }
}