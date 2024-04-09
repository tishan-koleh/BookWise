package com.example.bookwise.Member

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityMember1Binding
import com.example.bookwise.databinding.ActivityMemberBinding

class MemberActivity1 : AppCompatActivity() {
    private lateinit var binding:ActivityMember1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_member1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.button3.setOnClickListener {
            startActivity(Intent(this,MemberActivity::class.java))
        }

        onBackPressedDispatcher.addCallback {
            finishAffinity()
        }
    }
}