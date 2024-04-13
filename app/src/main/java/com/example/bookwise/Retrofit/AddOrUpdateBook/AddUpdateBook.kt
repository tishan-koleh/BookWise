package com.example.bookwise.Retrofit.AddOrUpdateBook

data class AddUpdateBook(
    val title: String,
    val quantity: Int,
    val author: Int,
    val genre: Int,
    val reservation: Int
)