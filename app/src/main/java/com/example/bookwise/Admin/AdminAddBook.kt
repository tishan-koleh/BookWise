package com.example.bookwise.Admin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityAdminActivityBinding
import com.example.bookwise.databinding.ActivityAdminAddBookBinding
import com.google.android.material.snackbar.Snackbar

class AdminAddBook : AppCompatActivity() {
    lateinit var activityAdminAddBook: ActivityAdminAddBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAdminAddBook = ActivityAdminAddBookBinding.inflate(layoutInflater)

        setContentView(activityAdminAddBook.root)


        activityAdminAddBook.bookAddedButton.setOnClickListener {
            onBackPressed()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Toast.makeText(this,"Books Added",Toast.LENGTH_SHORT).show()

        super.onBackPressed()
    }
}