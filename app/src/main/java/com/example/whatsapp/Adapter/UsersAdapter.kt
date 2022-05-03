package com.example.whatsapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.Models.Users
import com.example.whatsapp.R
import com.squareup.picasso.Picasso


class UsersAdapter(private val context: Context,private val list:ArrayList<Users>): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.profileImage)
        val name = itemView.findViewById<TextView>(R.id.userNameList)
        val lastMessage =  itemView.findViewById<TextView>(R.id.lastMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder= ViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = list[position]
        Picasso.get().load(users.profilepic).placeholder(R.drawable.avatar3).into(holder.image)
        holder.name.text = users.userName

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(newList: List<Users>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}