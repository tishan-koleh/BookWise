package com.example.bookwise.Retrofit.TransactionDetails

data class TransactionObject(
    val due_date: String,
    val id: Int,
    val payment: Any,
    val transaction_date: String
)