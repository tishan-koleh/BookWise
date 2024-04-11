package com.example.bookwise.Retrofit.Transaction

data class TransactionCreated(
    val due_date: String,
    val id: Int,
    val payment: Any,
    val transaction_date: String
)