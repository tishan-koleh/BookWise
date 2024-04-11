package com.example.bookwise.Retrofit

import com.example.bookwise.Authentication.Registration.Login.LoginResponse
import com.example.bookwise.Authentication.Registration.Registration
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Retrofit.GetRequestDataClasses.CardDetails
import com.example.bookwise.Retrofit.PostRequestsDataClasses.Login
import com.example.bookwise.Retrofit.PostRequestsDataClasses.User
import com.example.bookwise.Retrofit.PutRequestDataClass.ResetResponse
import com.example.bookwise.Retrofit.PutRequestDataClass.UserResetPassword
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/book")
    suspend fun getBookList():Response<BookList>


    @POST("/user/register")
    suspend fun registerUser(
       @Body user: User
    ): Response<Registration>

    @POST("/user/login")
    suspend fun loginUser(@Body login: Login):Response<LoginResponse>


    @PUT("/user/{id}")
    suspend fun resetPassword(
        @Path("id") id: String,
        @Body userResetPassword: UserResetPassword
    ): Response<ResetResponse>

    @GET("cards/customer/{id}")
    suspend fun getCardDetails(@Path("id") id: Int): Response<com.example.bookwise.Retrofit.GetCardsByUserId.CardDetails>

}