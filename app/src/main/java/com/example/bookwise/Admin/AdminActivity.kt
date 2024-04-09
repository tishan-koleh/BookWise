package com.example.bookwise.Admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityAdminActivityBinding

class AdminActivity : AdminBaseActivity() {

    lateinit var activityAdminHome: ActivityAdminActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAdminHome =ActivityAdminActivityBinding.inflate(layoutInflater)
        setContentView(activityAdminHome.root)


        activityAdminHome.adminBottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeOption_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.homeAdminFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.books_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.booksAdminFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.membership_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.membershipAdminFragment)
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.fine_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.fineAdminFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }


    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.i("MYTAG","Entered OnBackPRessed")
        val currentFragment = findNavController(R.id.admin_nav_host_fragment).currentDestination?.id
        if(currentFragment == R.id.homeAdminFragment){
            super.onBackPressed()
        }else{
            findNavController(R.id.admin_nav_host_fragment).navigate(R.id.homeAdminFragment)
        }
    }



}