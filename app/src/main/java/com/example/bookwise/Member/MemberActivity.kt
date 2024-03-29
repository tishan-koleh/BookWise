package com.example.bookwise.Member

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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
import com.example.bookwise.R
import com.example.bookwise.databinding.ActivityMemberBinding
import com.google.android.material.navigation.NavigationView

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_member)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Click menu button to open Navigation DRawer
        binding.toolbar.setNavigationOnClickListener {
            binding.main.open()
        }





       // val navController = findNavController(R.id.navigation_view)


       // State selector for Items
        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
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


            }

            return@setNavigationItemSelectedListener true


            }
    }

//    fun navigateFragments(fragment1 : String, fragment2: String){
//        var id: String
//
//    }

}