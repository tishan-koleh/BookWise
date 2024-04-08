package com.example.bookwise.Member

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.bookwise.MainActivity
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityMemberBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_member)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Click menu button to open Navigation Drawer
        binding.toolbar.setNavigationOnClickListener {
            binding.main.open()
        }


//        val menu = binding.navigationView.menu
//        if(supportFragmentManager.isCurrentFragment(R.id.homeFragment)){
//            menu.findItem(R.id.homeOption).isChecked = true
//        }
//        else if (supportFragmentManager.isCurrentFragment(R.id.booksFragment)){
//            menu.findItem(R.id.books).isChecked = true
//        }
//        else if(supportFragmentManager.isCurrentFragment(R.id.returnFragment)){
//            menu.findItem(R.id.return_books).isChecked = true
//        }
//        else{
//            menu.findItem(R.id.fine).isChecked = true
//        }







        drawerLayout = binding.main

//        //The Default Screen Is home Screen So selecting option Home By default
//       // val menu = binding.navigationView.menu
//        val menuItem = menu.findItem(R.id.homeOption)
//        menuItem.isChecked = true

        // Select Items To perform Desired Actions
        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            binding.main.close()
            Handler(Looper.myLooper()!!).postDelayed({
                when(it.itemId){
                    R.id.homeOption->{
                        findNavController(R.id.fragmentContainerView_member).navigate(R.id.homeFragment)
                    }
                    R.id.books ->{
                        findNavController(R.id.fragmentContainerView_member).navigate(R.id.booksFragment)
                    }
                    R.id.return_books ->{
                        findNavController(R.id.fragmentContainerView_member).navigate(R.id.returnFragment)
                    }
                    R.id.fine ->{
                        findNavController(R.id.fragmentContainerView_member).navigate(R.id.fineFragment)
                    }
                    R.id.logout->{
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                }
            },200)
             return@setNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {

        //Close the drawer if open else navigate back to home else perform default back Action

        val home = findNavController(R.id.fragmentContainerView_member).currentDestination?.id
        if(binding.main.isDrawerOpen(GravityCompat.START)){
            binding.main.close()
        } else if( home != R.id.homeFragment){
            findNavController(R.id.fragmentContainerView_member).navigate(R.id.homeFragment)
            val menu = binding.navigationView.menu
            val menuItem = menu.findItem(R.id.homeOption)
            menuItem.isChecked = true
        } else if(home == R.id.homeFragment){
            showExitAlertDialog()
        }
        else{
            super.onBackPressed()
        }
    }

    fun showExitAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to exit the app?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            // Exit the app
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            // Dismiss the dialog
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    fun close(){
        CoroutineScope(Dispatchers.Main).launch {
            binding.main.close()
        }
    }

    fun FragmentManager.isCurrentFragment(fragmentId: Int): Boolean {
        val currentFragment = findFragmentById(fragmentId)
        return currentFragment != null && currentFragment.isVisible
    }
}
