package com.example.fitlink.model

data class User(
    var id: String = "" ,
    var name: String= "" ,
    var nickname: String= "" ,
    var pictureURL: String= "" ,
    var email: String = "" ,
    var isEmailVerified: Boolean = false ,
    var familyName: String = "" ,
    var givenName: String = ""
)
