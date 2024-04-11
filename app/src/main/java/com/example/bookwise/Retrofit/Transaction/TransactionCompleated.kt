package com.example.bookwise.Retrofit.Transaction

data class TransactionCompleated(
    val id: Int,
    val transaction_id: TransactionId,
    val book_id: BookId
)