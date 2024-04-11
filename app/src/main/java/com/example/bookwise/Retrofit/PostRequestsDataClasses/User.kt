package com.example.bookwise.Retrofit.PostRequestsDataClasses



data class User(
    val name: String,
    val email: String,
    val address: String,
    val contact_no: String,
    val password: String,
    val role:Int
)