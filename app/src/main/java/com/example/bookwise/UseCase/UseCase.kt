package com.example.bookwise.UseCase

import androidx.lifecycle.MutableLiveData
import com.example.bookwise.Data.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UseCase {

    private val bookList = MutableLiveData<List<Book>>()
    val books
        get() = bookList



     fun generateBooks(){

        CoroutineScope(Dispatchers.Default).launch {
            val books = ArrayList<Book>()
            for (i in 1..50) {
                val title = "Title $i"
                val author = "Author $i"
                val category = "Category $i"
                val quantity = (1..20).random() // Random quantity between 1 and 20
                books.add(Book(title, author, category, quantity))

            }
            bookList.postValue(books)
        }

    }



}