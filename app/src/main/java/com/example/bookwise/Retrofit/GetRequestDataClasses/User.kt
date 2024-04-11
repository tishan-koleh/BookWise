package com.example.bookwise.Retrofit.GetRequestDataClasses

data class User(
    val address: String,
    val contact_no: String,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: Role
)