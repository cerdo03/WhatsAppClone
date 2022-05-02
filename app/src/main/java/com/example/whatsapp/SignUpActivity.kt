package com.example.whatsapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whatsapp.Models.Users
import com.example.whatsapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        supportActionBar?.hide()

        progressDialog  = ProgressDialog(this)
        progressDialog.setTitle("Creating account")
        progressDialog.setMessage("We are creating your account. Please wait.")

        binding.btnSignUp.setOnClickListener {
            if(!binding.txtUsername.text.toString().isEmpty() && !binding.txtEmail.text.toString().isEmpty()
                && !binding.txtPassword.text.toString().isEmpty()){
                    progressDialog.show()
                mAuth.createUserWithEmailAndPassword(binding.txtEmail.text.toString(),
                    binding.txtPassword.text.toString()).addOnCompleteListener(this) { task ->

                    progressDialog.dismiss()
                    if (task.isSuccessful) {

                        val user = Users(binding.txtUsername.text.toString(),binding.txtEmail.text.toString()
                            ,binding.txtPassword.text.toString(),
                        )
                        val id = task.result.user!!.uid
                        database.reference.child("Users").child(id).setValue(user)
                        Toast.makeText(this, "Sign Up Successful",
                            Toast.LENGTH_SHORT).show()

//
                    } else {

                        Toast.makeText(this, task.exception?.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else{
                Toast.makeText(this,"Enter Credentials",Toast.LENGTH_SHORT).show()
            }
        }

        binding.AlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

    }
}