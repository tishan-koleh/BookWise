package com.example.bookwise.Admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bookwise.R
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.ActivityAdminActivityBinding
import com.example.bookwise.databinding.ActivityAdminAddBookBinding
import com.google.android.material.snackbar.Snackbar

class AdminAddBook : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var apiService: ApiService
    private lateinit var factory: MainVIewModelFactory
    lateinit var activityAdminAddBook: ActivityAdminAddBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAdminAddBook = ActivityAdminAddBookBinding.inflate(layoutInflater)

        setContentView(activityAdminAddBook.root)
        apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
        factory = MainVIewModelFactory(apiService)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]


        val title = activityAdminAddBook.bookTitle.text.toString()
        val author = activityAdminAddBook.authorName.text.toString()
        val genre = activityAdminAddBook.genreName.text.toString()
        val quantityString = activityAdminAddBook.bookQuantity.text.toString()
        val quantity = if (quantityString.isBlank()) {
            // Show an error message or use a default value
            //Toast.makeText(this, "Quantity cannot be empty", Toast.LENGTH_SHORT).show()
            0
        } else {
            quantityString.toInt()
        }

        activityAdminAddBook.bookAddedButton.setOnClickListener {


            viewModel.addOrUpdateBook(author,genre, quantity, title)
        }

        viewModel.bookAddedOrUpdatedData.observe(this, Observer {
            startActivity(Intent(this,AdminActivity::class.java))
            Toast.makeText(this,"${title} Added",Toast.LENGTH_LONG).show()
        })

    }


}