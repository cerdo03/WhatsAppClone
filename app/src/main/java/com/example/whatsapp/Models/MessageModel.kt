package com.example.whatsapp.Models

import java.sql.Timestamp

class MessageModel {
    var uId:String? = null
    var message:String?= null
    var messageId:String? = null
    var timeStamp :Long?= null

    constructor(uId:String,message:String,messageId:String,timestamp: Long){
        this.uId = uId
        this.message = message
        this.messageId = messageId
        this.timeStamp = timeStamp

    }
    constructor(uId:String,message:String){
        this.uId = uId
        this.message = message
    }
    constructor(){

    }
}