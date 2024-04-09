package com.example.bookwise.Retrofit

import com.example.bookwise.Authentication.Registration.Login.LoginResponse
import com.example.bookwise.Authentication.Registration.Registration
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.PostRequestsDataClasses.Login
import com.example.bookwise.PostRequestsDataClasses.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/book")
    suspend fun getBookList():Response<BookList>


    @POST("/user/register")
    suspend fun registerUser(
       @Body user: User
    ): Response<Registration>

    @POST("/user/login")
    suspend fun loginUser(@Body login: Login):Response<LoginResponse>

}