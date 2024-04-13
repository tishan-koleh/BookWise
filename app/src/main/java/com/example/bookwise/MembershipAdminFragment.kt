package com.example.bookwise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookwise.RecyclerView.ProgrammingAdapterUserList
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentMembershipAdminBinding
import com.example.bookwise.databinding.FragmentMembershipAdminBindingImpl
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MembershipAdminFragment : Fragment() {
    private lateinit var binding: FragmentMembershipAdminBinding
    private lateinit var apiService: ApiService
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : ProgrammingAdapterUserList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_membership_admin, container, false)
        apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(apiService)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        adapter = ProgrammingAdapterUserList(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerViewMembers
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            val users = apiService.getAllUsers().body()
            Log.i("MYTAG",apiService.getAllUsers().isSuccessful.toString())
            if(users==null){
                adapter.submitList(listOf())
            }else {
                adapter.updateList(users)
            }
        }
        recyclerView.adapter = adapter

        viewModel.deleteAlert.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                val users = apiService.getAllUsers().body()
                Log.i("MYTAG",apiService.getAllUsers().isSuccessful.toString())
                if(users==null){
                    adapter.submitList(listOf())
                }else {
                    adapter.submitList(users)
                }
            }
        })

        val searchView = binding.searchMember
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
    }


}