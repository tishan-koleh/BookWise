package com.example.bookwise.PutRequestDataClass

data class ResetResponse(
    val address: String,
    val contact_no: String,
    val customer: Any,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: Any
)