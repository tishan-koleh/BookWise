package com.example.bookwise.Retrofit.TransactionDetails

data class TransactionDetailsItem(
    val book_object: BookObject,
    val transaction_object: TransactionObject,
    val user_name: String
)