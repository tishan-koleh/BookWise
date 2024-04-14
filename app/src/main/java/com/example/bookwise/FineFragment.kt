package com.example.bookwise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.bookwise.Member.MemberActivity
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper
import com.example.bookwise.databinding.FragmentFineBinding
import com.example.bookwise.databinding.FragmentReturnBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FineFragment : Fragment() {
    private lateinit var binding: FragmentFineBinding
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
//        val finekOption = navigationView.menu.findItem(R.id.fine)
//        finekOption.isChecked = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fine, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menu = (activity as MemberActivity).findViewById<NavigationView>(R.id.navigation_view).menu
        menu.findItem(R.id.fine).isChecked = true

        if(SharedPreferencesHelper.readInt(Utils.user_fine,-1) != -2){
            val apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
            lifecycleScope.launch {
                binding.fineTextView.text = apiService.getFineByUserId(SharedPreferencesHelper.readInt(Utils.user_id,-1)).body().toString()
                Log.i("MYTAG",apiService.getFineByUserId(SharedPreferencesHelper.readInt(Utils.user_id,-1)).isSuccessful.toString())
                var fine = apiService.getFineByUserId(SharedPreferencesHelper.readInt(Utils.user_id,-1)).body()
                SharedPreferencesHelper.writeInt(Utils.user_fine,fine!!)
            }
        }
        if(binding.fineTextView.text == "0")
        {
            binding.buttonClearDues.isEnabled = false

        }
        else{
            binding.buttonClearDues.isEnabled = true
            binding.buttonClearDues.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Dues")
                    .setMessage("Do you want to clear your dues?")
                    .setPositiveButton("OK") { dialog, _ ->
                        binding.fineTextView.text = "0"
                        binding.buttonClearDues.isEnabled = false
                        SharedPreferencesHelper.writeInt(Utils.user_fine,-2)
                        dialog.dismiss()
                    }
                    .show()
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FineFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}