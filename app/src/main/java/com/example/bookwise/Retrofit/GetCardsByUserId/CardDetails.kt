package com.example.bookwise.Retrofit.GetCardsByUserId

data class CardDetails(
    val customer: Customer,
    val expiredDate: String,
    val id: Int,
    val issuedDate: String,
    val status: Boolean,
    val transactions: List<Any>
)