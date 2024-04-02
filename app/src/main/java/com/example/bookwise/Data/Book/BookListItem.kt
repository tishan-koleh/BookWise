package com.example.bookwise.Data.Book

data class BookListItem(
    val author: Author,
    val genre: Genre,
    val id: Int,
    val quantity: Int,
    val title: String
)