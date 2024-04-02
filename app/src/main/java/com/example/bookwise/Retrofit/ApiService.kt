package com.example.bookwise.Retrofit

import com.example.bookwise.Data.Book.BookList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/book")
    suspend fun getBookList():Response<BookList>

}