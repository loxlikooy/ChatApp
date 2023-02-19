package com.example.chatapp

class User {
    var txt_name: String?=null
    var email:String?= null
    var uid: String?= null
    constructor(){}

    constructor(txt_name: String?, email: String?, uid: String?){
    this.txt_name= txt_name
    this.email = email
    this.uid = uid
    }
}