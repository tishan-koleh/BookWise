package com.example.bookwise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookwise.Member.MemberActivity1
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.util.Random

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private var param1: String? = null
    private var param2: String? = null

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        val retrofitService  = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(retrofitService)
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("ID-CARD",SharedPreferencesHelper.readInt(Utils.user_id,-1).toString())
        viewModel.getCardDetails(SharedPreferencesHelper.readInt(Utils.user_id,-1))

        viewModel.cardLoading.observe(viewLifecycleOwner, Observer {
            if(it==true){
                binding.memberIdLayout.visibility = View.GONE
                binding.progressBarMemberHome.visibility = View.VISIBLE
            }else{
                binding.progressBarMemberHome.visibility = View.GONE
                binding.memberIdLayout.visibility = View.VISIBLE
            }
        })
        viewModel.cardErrorDetails.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(requireContext())
                .setTitle("Network Error")
                .setMessage("Some Error Occured")
                .setPositiveButton("OK") { dialog, _ ->
                    startActivity(Intent(requireActivity(),MemberActivity1::class.java))
                    dialog.dismiss()
                }
                .show()


        })

        viewModel.cardDetails.observe(viewLifecycleOwner, Observer {
            Log.i("IN-OBSERVER",it.customer.name)
            binding.textViewMemberName.text = it.customer.user.name
            binding.memberId.text = "DT-"+it.customer.user.id.toString()
            binding.cardId.text = "CT-"+it.id.toString()
            binding.contactNo.text = it.customer.user.contact_no
            binding.joiningDateTv.text = it.issuedDate.substring(0,7)

        })
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}