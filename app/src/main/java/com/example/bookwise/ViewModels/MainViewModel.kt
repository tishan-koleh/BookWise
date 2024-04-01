package com.example.bookwise.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookwise.Data.Book
import com.example.bookwise.UseCase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: UseCase):ViewModel() {

    val books: LiveData<List<Book>> = useCase.books

    fun getBookList() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.generateBooks()
        }
    }
}