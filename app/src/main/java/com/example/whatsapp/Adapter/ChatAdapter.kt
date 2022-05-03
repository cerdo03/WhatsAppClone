package com.example.whatsapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.whatsapp.Models.MessageModel
import com.example.whatsapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    val messageModel = ArrayList<MessageModel>()
    val SENDER_VIEW_TYPE=1
    val RECIEVER_VIEW_TYPE=2
     class RecieverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val recieverMsg = itemView.findViewById<TextView>(R.id.recieverText)
        val recieverTime = itemView.findViewById<TextView>(R.id.recieverTime)
    }
    class SenderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val senderMsg = itemView.findViewById<TextView>(R.id.senderText)
        val senderTime = itemView.findViewById<TextView>(R.id.senderTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==SENDER_VIEW_TYPE){
            return SenderViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false))
        }
        else{
            return RecieverViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msgModel = messageModel[position]
        if(holder::class.java == SenderViewHolder::class.java){
            (SenderViewHolder(holder.itemView).senderMsg).text = msgModel.message
        }
        if(holder::class.java == RecieverViewHolder::class.java){
            (RecieverViewHolder(holder.itemView).recieverMsg).text = msgModel.message
        }
    }

    override fun getItemCount(): Int {
        return messageModel.size
    }

    override fun getItemViewType(position: Int): Int {
        if(messageModel.get(position).uId.equals(FirebaseAuth.getInstance().uid)){
            return SENDER_VIEW_TYPE
        }
        else{
            return RECIEVER_VIEW_TYPE
        }
    }
}