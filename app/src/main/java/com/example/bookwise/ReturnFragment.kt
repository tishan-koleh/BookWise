package com.example.bookwise

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookwise.Member.MemberActivity
import com.example.bookwise.RecyclerView.ProgrammingAdapter
import com.example.bookwise.RecyclerView.ProgrammingAdapterBorrowedBookItem
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentReturnBinding
import com.google.android.material.navigation.NavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReturnFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReturnFragment : Fragment() {
    private lateinit var binding : FragmentReturnBinding
    private lateinit var viewModel: MainViewModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        val activity = requireActivity()
//        val navigationView = activity.findViewById<NavigationView>(R.id.main)
//        val returnOption = navigationView.menu.findItem(R.id.return_books)
//        returnOption.isChecked = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_return, container, false)
        val apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(apiService)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menu = (activity as MemberActivity).findViewById<NavigationView>(R.id.navigation_view).menu
        menu.findItem(R.id.return_books).isChecked = true

        val recyclerView = binding.recyclerViewReturnMember
        val adapter = ProgrammingAdapterBorrowedBookItem(viewModel)
        adapter.submitList(listOf())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter  = adapter

        viewModel.getBorrowedBooksByCardId(SharedPreferencesHelper.readInt(Utils.card_id,-1))

        viewModel.cardLogetBorrowedBooksadingData.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgressBar()
                binding.recyclerViewReturnMember.visibility = View.GONE
            } else {
                Handler(Looper.myLooper()!!).postDelayed({
                    hideProgressBar()
                    binding.recyclerViewReturnMember.visibility = View.VISIBLE
                },1000)

            }
        })

        viewModel.getBorrowedBooksData.observe(viewLifecycleOwner, Observer {

            Log.i("IN OBSERVER RETURN FARGMENT",it.toString())
            adapter.submitList(it)
        })

    }

    private fun showProgressBar() {
        binding.progressbarMemeberReturn.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressbarMemeberReturn.visibility = View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReturnFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}