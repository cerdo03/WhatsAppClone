package com.example.whatsapp.Models

class Users {
    var profilepic: String? = null
    var userName: String? = null
    var mail: String? = null
    var password: String? = null
    var userId: String? = null
    var lastMessage: String? = null
    var status: String? = null

    constructor(){

    }

    constructor(
        profilepic: String?,
        userName: String?,
        mail: String?,
        password: String?,
        userId: String?,
        lastMessage: String?,
        status: String?,
    ) {
        this.profilepic = profilepic
        this.userName = userName
        this.mail = mail
        this.password = password
        this.userId = userId
        this.lastMessage = lastMessage
        this.status = status
    }

    constructor(userName: String?, mail: String?,password: String?) {
        this.userName = userName
        this.mail = mail
        this.password = password
    }



}