package com.example.bookwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookwise.Admin.AdminActivity
import com.example.bookwise.RecyclerView.ProgrammingAdapterAllTransactionDetails
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.databinding.FragmentFineAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FineAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FineAdminFragment : Fragment() {

    private lateinit var binding:FragmentFineAdminBinding
    private lateinit var apiService: ApiService
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    override fun onResume() {
//        super.onResume()
//        val navView = activity?.findViewById<NavigationView>(R.id.admin_nav_host_fragment)
//        navView?.setCheckedItem(R.id.fine_admin)
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
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fine_admin, container, false)
        apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeOption = (activity as AdminActivity).findViewById<BottomNavigationView>(R.id.admin_bottom_navigation).menu
        homeOption.findItem(R.id.fine_admin).isChecked = true
        val recyclerView = binding.transactionDetailsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ProgrammingAdapterAllTransactionDetails()
        lifecycleScope.launch {
            adapter.updateList(apiService.getAllTransactionDetails().body()!!)
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val searchView = binding.transationSearchView
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FineAdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FineAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}