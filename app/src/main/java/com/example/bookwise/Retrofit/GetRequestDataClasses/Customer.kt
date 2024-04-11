package com.example.bookwise.Retrofit.GetRequestDataClasses

data class Customer(
    val address: String,
    val contact_no: String,
    val email: String,
    val id: Int,
    val name: String,
    val user: User
)