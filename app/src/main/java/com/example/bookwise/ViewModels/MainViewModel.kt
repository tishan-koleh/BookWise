package com.example.bookwise.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Data.Book.BookListItem
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.UseCase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: ApiService):ViewModel() {


    //Show Books to the Member

    private val bookLiveData = MutableLiveData<BookList?>()
    val book:LiveData<BookList?>
        get() = bookLiveData


    private val loadingLiveData = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean>
        get() = loadingLiveData


    private val errorLiveData = MutableLiveData<String>()
    val error :LiveData<String>
        get() = errorLiveData


     fun getBookList(){
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            try {
                val response = apiService.getBookList()
                if(response.isSuccessful){
                    val body = response.body()
                    bookLiveData.postValue(body)
                }
                else{
                    errorLiveData.postValue("Error ${response.errorBody()}")
                }
            }
            catch (e:Exception){
                errorLiveData.postValue("Error ${e.message}")
            }
            finally {
                loadingLiveData.postValue(false)
            }
        }
    }


    //Show Books to the Member

}