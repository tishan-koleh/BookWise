package com.example.bookwise

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookwise.Admin.AdminActivity
import com.example.bookwise.Admin.AdminAddBook
import com.example.bookwise.Admin.AdminBaseActivity
import com.example.bookwise.RecyclerView.ProgrammingAdapter
import com.example.bookwise.RecyclerView.ProgrammingAdapterAdminBooks
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentBooksAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import retrofit2.Retrofit
import retrofit2.create

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksAdminFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentBooksAdminBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



//    override fun onResume() {
//        super.onResume()
//        val navView = activity?.findViewById<NavigationView>(R.id.admin_nav_host_fragment)
//        navView?.setCheckedItem(R.id.books_admin)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_books_admin, container, false)
        showProgressBar()
        binding.recyclerViewAdminBooks.visibility = View.GONE
        val apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(apiService)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeOption = (activity as AdminActivity).findViewById<BottomNavigationView>(R.id.admin_bottom_navigation).menu
        homeOption.findItem(R.id.books_admin).isChecked = true
        binding.addBookButton.setOnClickListener {
            startActivity(Intent(this@BooksAdminFragment.context,AdminAddBook::class.java))
        }
        val recyclerView = binding.recyclerViewAdminBooks
        val adapter = ProgrammingAdapterAdminBooks(viewModel)
        adapter.submitList(listOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getBookList()
        viewModel.book.observe(viewLifecycleOwner, Observer {

            adapter.updateList(it!!)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgressBar()
                binding.recyclerViewAdminBooks.visibility = View.GONE
            } else {
                Handler().postDelayed({
                    hideProgressBar()
                    binding.recyclerViewAdminBooks.visibility = View.VISIBLE
                },1000)

            }
        })


        val searchView = binding.searchBooksAdmin
        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return true
            }
        })

        viewModel.valAlertBookAddedData.observe(viewLifecycleOwner, Observer {
            if(it == "Please add some quantity!")
            {
                AlertDialog.Builder(requireContext())
                    .setTitle("Book")
                    .setMessage(it)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Book")
                    .setMessage(it)
                    .setPositiveButton("OK") { dialog, _ ->
                        viewModel.addOrUpdateBook()
                        viewModel.getBookList()
                        dialog.dismiss()
                    }
                    .show()
            }

        })

    }

    private fun showProgressBar() {
        binding.progressBarAdminBook.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarAdminBook.visibility = View.GONE
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksAdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}