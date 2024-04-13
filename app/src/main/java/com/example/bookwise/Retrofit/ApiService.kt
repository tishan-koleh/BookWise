package com.example.bookwise.Retrofit

import com.example.bookwise.Authentication.Registration.Login.LoginResponse
import com.example.bookwise.Authentication.Registration.Registration
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Retrofit.BorrowedBooksByCardId.BorrowedBooksByCardId
import com.example.bookwise.Retrofit.GetAllUsers.UserList
import com.example.bookwise.Retrofit.PostRequestsDataClasses.Login
import com.example.bookwise.Retrofit.PostRequestsDataClasses.User
import com.example.bookwise.Retrofit.PutRequestDataClass.ResetResponse
import com.example.bookwise.Retrofit.PutRequestDataClass.UserResetPassword
import com.example.bookwise.Retrofit.Transaction.BorrowBookDetails
import com.example.bookwise.Retrofit.Transaction.ToCreateTransaction
import com.example.bookwise.Retrofit.Transaction.TransactionCompleated
import com.example.bookwise.Retrofit.Transaction.TransactionCreated
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @POST("/transaction")
    suspend fun initiateTransaction(@Body toCreateTransaction: ToCreateTransaction):Response<TransactionCreated>


    @POST("/transactionDetails")
    suspend fun borrowBook(@Body borrowBookDetails: BorrowBookDetails): Response<TransactionCompleated>

    @GET("/transaction/TransactionByCardCount/{cardId}")
    suspend fun getNoOfBorrowedBooksByUser(@Path("cardId") card_id:Int):Response<Int>

    @GET("/book/availableQuantity")
    suspend fun getTotalNoOfBooks():Response<Int>


    @GET("/payment/totalFine")
    suspend fun getTotalFine():Response<Int>

    @GET("/cards/activecard")
    suspend fun getActiveMembers():Response<Int>

    @GET("/book/borrowedBooks")
    suspend fun getTotalBorrowedBooks():Response<Int>


    @GET("/transaction/TransactionByCardId/{cardId}")
    suspend fun getBorrowedBooksByCardId(@Path("cardId") card_id :Int):Response<BorrowedBooksByCardId>

    @GET("/payment/totalFineForUser/{user_id}")
    suspend fun getFineByUserId(@Path("user_id") user_id:Int):Response<Int>


    @GET("/user")
    suspend fun getAllUsers():Response<UserList>

    @DELETE("/user/{id}")
    suspend fun deleteUser(@Path("id") id:Int):Response<Unit>


}