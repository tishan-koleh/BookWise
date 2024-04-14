package com.example.bookwise.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.bookwise.MainActivity
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityAdminActivityBinding

class AdminActivity : AdminBaseActivity() {


    override fun onLogoutButtonClick() {

        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->

                dialog.dismiss()
            }
            .show()
        Log.i("Admin", "Sucess")

    }

    lateinit var activityAdminHome: ActivityAdminActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAdminHome = ActivityAdminActivityBinding.inflate(layoutInflater)
        setContentView(activityAdminHome.root)


        activityAdminHome.adminBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeOption_admin -> {
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.homeAdminFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.books_admin -> {
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.booksAdminFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.membership_admin -> {
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.membershipAdminFragment)
                    return@setOnNavigationItemSelectedListener true

                }

                R.id.fine_admin -> {
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.fineAdminFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }






    }
}