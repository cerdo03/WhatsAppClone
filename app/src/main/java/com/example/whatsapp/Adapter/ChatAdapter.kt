package com.example.whatsapp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.whatsapp.Models.MessageModel
import com.example.whatsapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter(): Adapter<RecyclerView.ViewHolder>(){
    var context: Context? = null
    var messageModel :ArrayList<MessageModel>? = null
    lateinit var msgModel:MessageModel
    var recId:String? = null
    constructor( context: Context, messageModel: ArrayList<MessageModel>,recId:String?) : this() {
        this.context=context
        this.messageModel=messageModel
        this.recId=recId
    }
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
        val size = messageModel?.size

        if(size!=null){
             msgModel = messageModel!!.get(size-position-1)
        }
        else{
            msgModel = messageModel!!.get(position)
        }

        if(holder::class.java == SenderViewHolder::class.java){
            (SenderViewHolder(holder.itemView).senderMsg).text = msgModel?.message
            val date = Date(msgModel?.timeStamp!!)
            val simpleDateFormat = SimpleDateFormat("h:mm a")
            val strDate = simpleDateFormat.format(date)
            (SenderViewHolder(holder.itemView).senderTime).text = strDate

        }
        if(holder::class.java == RecieverViewHolder::class.java){
            (RecieverViewHolder(holder.itemView).recieverMsg).text = msgModel?.message

            val date: Date = Date(msgModel?.timeStamp!!)
            val simpleDateFormat = SimpleDateFormat("h:mm a")
            val strDate = simpleDateFormat.format(date)
            (RecieverViewHolder(holder.itemView).recieverTime).text = strDate
        }
        holder.itemView.setOnLongClickListener ( object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete Message")
                builder.setMessage("Are you sure you want to delete this message? \n" +
                        "This message would be deleted only from your device.")

                builder.setPositiveButton("Yes") { dialog, which ->
                    val database = FirebaseDatabase.getInstance()
                    val senderRoom = FirebaseAuth.getInstance().uid + recId
//                    val recieverRoom =
                    if (msgModel != null) {
                        database.reference.child("chats").child(senderRoom)
                            .child(msgModel.messageId.toString()).setValue(null)
                    }

                }

                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                builder.show()
                return false
            }

        }
        )
    }

    override fun getItemCount(): Int {
        return messageModel?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        if(messageModel?.get(position)?.uId.equals(FirebaseAuth.getInstance().uid)){
            return SENDER_VIEW_TYPE
        }
        else{
            return RECIEVER_VIEW_TYPE
        }
    }
}