package com.example.bookwise.Retrofit.BorrowedBooksByCardId

data class BorrowedBooksByCardIdItem(
    val author: Author,
    val genre: Genre,
    val id: Int,
    val quantity: Int,
    val reservation: Int,
    val reservedUsers: List<Any>,
    val stock: Int,
    val title: String
)