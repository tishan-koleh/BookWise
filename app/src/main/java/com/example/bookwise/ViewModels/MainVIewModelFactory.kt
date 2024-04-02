package com.example.bookwise.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.UseCase.UseCase

class MainVIewModelFactory(private val apiService: ApiService):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(apiService) as T
    }
}