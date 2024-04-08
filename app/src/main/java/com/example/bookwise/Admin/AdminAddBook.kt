package com.example.bookwise.Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityAdminAddBookBinding

class AdminAddBook : AppCompatActivity() {
    lateinit var activityAdminAddBook: ActivityAdminAddBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_book)

    }
}