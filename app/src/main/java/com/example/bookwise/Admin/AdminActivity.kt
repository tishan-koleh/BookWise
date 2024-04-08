package com.example.bookwise.Admin

import android.os.Bundle
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

                }
                R.id.books_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.booksAdminFragment)
                }
                R.id.membership_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.membershipAdminFragment)

                }
                R.id.fine_admin->{
                    findNavController(R.id.admin_nav_host_fragment).navigate(R.id.adminAddBooksFragment)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }
}