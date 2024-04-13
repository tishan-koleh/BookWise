package com.example.bookwise.Retrofit.AddOrUpdateBook

data class AddUpdateBook(
    val author: Int,
    val genre: Int,
    val quantity: Int,
    val reservation: Int,
    val title: String
)