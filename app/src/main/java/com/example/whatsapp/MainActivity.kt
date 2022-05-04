package com.example.whatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.example.whatsapp.Adapter.FragmentsAdapter
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    var setBackground = false

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
            finish()

        }else if(item.itemId == R.id.groupChat){

        }
        else if(item.itemId == R.id.background){
            if(!setBackground){
                val layout = findViewById<FrameLayout>(R.id.chatLayout)
                layout.background = resources.getDrawable(R.color.lightGrey)
                setBackground=true
            }
            else{
                val layout = findViewById<FrameLayout>(R.id.chatLayout)
                layout.background = resources.getDrawable(R.drawable.w2)
                setBackground=false
            }

        }
        return super.onOptionsItemSelected(item)
    }
}