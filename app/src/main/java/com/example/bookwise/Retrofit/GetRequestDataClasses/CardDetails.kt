package com.example.bookwise.Retrofit.GetRequestDataClasses

data class CardDetails(
    val id: Int,
    val customer: Customer,
    val issuedDate: String,
    val expiredDate: String,
    val status: Boolean,
    val transactions: List<Any>
)