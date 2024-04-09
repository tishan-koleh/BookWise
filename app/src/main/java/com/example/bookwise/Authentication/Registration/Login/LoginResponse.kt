package com.example.bookwise.Authentication.Registration.Login

data class LoginResponse(
    val User: User,
    val message: String,
    val sucess: Boolean
)