package com.example.bookwise.Admin

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookwise.R

open class AdminBaseActivity : AppCompatActivity() {
    lateinit var layout: ConstraintLayout
    override fun setContentView(view: View?) {

        layout = layoutInflater.inflate(R.layout.activity_admin_base,null) as ConstraintLayout
        val container : FrameLayout =layout.findViewById(R.id.admin_activity_container)
        container.addView(view)
        super.setContentView(layout)
    }











}