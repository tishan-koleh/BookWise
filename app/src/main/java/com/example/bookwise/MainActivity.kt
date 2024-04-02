package com.example.bookwise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bookwise.Member.MemberActivity
import com.example.bookwise.Member.MemberActivity1
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.UseCase.UseCase
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.ActivityMainBinding
import com.example.bookwise.databinding.ActivityMemberBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val retrofitService  = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(retrofitService)
        val viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        viewModel.book.observe(this, Observer {
             val bookList = it?.listIterator()
            if(bookList!=null){
                while (bookList.hasNext()){
                    var book = bookList.next()
                    Log.i("Books",book.title)
                }
            }
        })
    }
}