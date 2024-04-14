package com.example.bookwise.Admin

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.bookwise.MainActivity
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityAdminBaseBinding

open class AdminBaseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBaseBinding
    lateinit var layout: ConstraintLayout


    override fun setContentView(view: View?) {

        layout = layoutInflater.inflate(R.layout.activity_admin_base, null) as ConstraintLayout
        val container: FrameLayout = layout.findViewById(R.id.admin_activity_container)
        container.addView(view)
        super.setContentView(layout)
        // Set click listener after inflating the layout
        binding = ActivityAdminBaseBinding.bind(layout)
        binding.logoutButton.setOnClickListener {
            onLogoutButtonClick()
        }

    }



    open fun onLogoutButtonClick() {
        Log.i("Base", "Success")
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Optionally finish the current activity
    }















}