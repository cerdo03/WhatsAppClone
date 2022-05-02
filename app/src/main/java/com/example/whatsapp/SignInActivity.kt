package com.example.whatsapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whatsapp.databinding.ActivitySignInBinding
import com.example.whatsapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        supportActionBar?.hide()

        setContentView(binding.root)



        progressDialog  = ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Validating your credentials...")

        binding.btnSignIn.setOnClickListener {
            if(!binding.txtEmail.text.toString().isEmpty()
                && !binding.txtPassword.text.toString().isEmpty()){
                progressDialog.show()
                mAuth.signInWithEmailAndPassword(binding.txtEmail.text.toString(),
                    binding.txtPassword.text.toString()).addOnCompleteListener(this) { task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful) {
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, task.exception?.message.toString(),
                            Toast.LENGTH_SHORT).show()

                    }
                }

            }
            else{
                Toast.makeText(this,"Enter Credentials", Toast.LENGTH_SHORT).show()
            }


        }
        if(mAuth.currentUser!=null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.clickSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }


    }
}