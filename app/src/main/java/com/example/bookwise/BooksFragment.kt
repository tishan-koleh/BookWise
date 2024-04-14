package com.example.bookwise

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Member.MemberActivity
import com.example.bookwise.RecyclerView.ProgrammingAdapter
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.UseCase.UseCase
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentBooksBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksFragment : Fragment() {
    private lateinit var binding : FragmentBooksBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter:ProgrammingAdapter
    private lateinit var retrofitService : ApiService
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_books, container, false)
         retrofitService  = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(retrofitService)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menu = (activity as MemberActivity).findViewById<NavigationView>(R.id.navigation_view).menu
        menu.findItem(R.id.books).isChecked = true

            val recyclerView = binding.recyclerviewBooks
            adapter = ProgrammingAdapter(viewModel)
            adapter.submitList(listOf())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewModel.getBookList()
            viewModel.book.observe(viewLifecycleOwner, Observer {

                adapter.submitList(it)
            })

            viewModel.isLoading.observe(viewLifecycleOwner, Observer {
                if (it) {
                    showProgressBar()
                    binding.totalBooksAvailableTv.visibility = View.GONE
                    binding.recyclerviewBooks.visibility = View.GONE
                } else {
                    Handler().postDelayed({
                        hideProgressBar()
                        binding.totalBooksAvailableTv.visibility = View.VISIBLE
                        binding.recyclerviewBooks.visibility = View.VISIBLE
                    },1000)

                }
            })

        lifecycleScope.launch {
            binding.totalBooksAvailableTv.text = "Total Books Available : "+retrofitService.getTotalNoOfBooks().body().toString()
        }



            viewModel.error.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.homeFragment)
                Toast.makeText(
                    this@BooksFragment.requireContext(),
                    "Some Error Occured",
                    Toast.LENGTH_LONG
                ).show()
            })

            viewModel.alert.observe(viewLifecycleOwner, Observer {


                    AlertDialog.Builder(requireContext())
                        .setTitle("Issue")
                        .setMessage("Book Issued")
                        .setPositiveButton("OK") { dialog, _ ->
                            viewModel.getBookList()
                            dialog.dismiss()
                        }
                        .show()


            })




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = viewModel.book.value?.filter { book ->
                    book.title.contains(newText ?: "", ignoreCase = true)
                }
                adapter.submitList(filteredList)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}