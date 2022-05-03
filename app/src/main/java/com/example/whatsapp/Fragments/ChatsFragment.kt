package com.example.whatsapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.Adapter.UsersAdapter
import com.example.whatsapp.Models.Users
import com.example.whatsapp.R
import com.example.whatsapp.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatsFragment : Fragment() {


    lateinit var binding: FragmentChatsBinding
    val list=ArrayList<Users>()
    lateinit var database:FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentChatsBinding.inflate(inflater,container,false)
        database = FirebaseDatabase.getInstance()
        val adapter = context?.let { UsersAdapter(it,list) }

        binding.chatRecyclerView.adapter = adapter
        val manager = LinearLayoutManager(context)
        binding.chatRecyclerView.layoutManager = manager
        database.reference.child("Users").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    list.clear()
                    for(i  in dataSnapshot.children){
                        val users = i.getValue(Users::class.java)
                        users?.userId = i.key
                        if (users != null && !users?.userId.equals(FirebaseAuth.getInstance().uid))  {
                            list.add(users)
                        }
                    }
                    adapter?.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                }
            }
        )
        // Inflate the layout for this fragment
        return binding.root
    }


}