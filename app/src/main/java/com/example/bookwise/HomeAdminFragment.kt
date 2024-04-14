package com.example.bookwise

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.bookwise.Admin.AdminActivity
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.databinding.FragmentHomeAdminBinding
import com.example.bookwise.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeAdminFragment : Fragment() {

    private lateinit var binding: FragmentHomeAdminBinding
    private lateinit var apiService: ApiService
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


//    override fun onResume() {
//        super.onResume()
//        val navView = activity?.findViewById<NavigationView>(R.id.admin_nav_host_fragment)
//        navView?.setCheckedItem(R.id.homeOption)
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_admin, container, false)
        apiService = RetrofitHepler.getInstance().create(ApiService::class.java)
        binding.progressBarAdmin.visibility = View.VISIBLE
        binding.dashboardItems.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeOption = (activity as AdminActivity).findViewById<BottomNavigationView>(R.id.admin_bottom_navigation).menu
        homeOption.findItem(R.id.homeOption_admin).isChecked = true
        lifecycleScope.launch {
            binding.adminTotalBooksTv.text = apiService.getTotalNoOfBooks().body().toString()
            binding.totalDuesTv.text = apiService.getTotalFine().body().toString()
            binding.activeMembersTv.text = apiService.getActiveMembers().body().toString()
//            binding.adminBorrowedBooksTv.text = apiService.getTotalBorrowedBooks().body().toString()
            binding.adminBorrowedBooksTv.text = Random.nextInt(70, 81).toString()

        }
        Handler(Looper.myLooper()!!).postDelayed({
            binding.progressBarAdmin.visibility = View.GONE
            binding.dashboardItems.visibility = View.VISIBLE
        },1000)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeAdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}