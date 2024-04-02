package com.example.bookwise.UseCase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import org.jetbrains.annotations.Async

class UseCase(private val apiService: ApiService) {

    private val bookList = MutableLiveData<BookList>()
    val books:LiveData<BookList>
        get() = bookList



     suspend fun generateBooks(){

         CoroutineScope(Dispatchers.IO).launch {
             val response = apiService.getBookList()
             val body = response.body()
             if(body != null){
                 bookList.postValue(body!!)
             }
         }

    }



}