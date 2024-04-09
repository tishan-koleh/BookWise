package com.example.bookwise

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookwise.Member.MemberActivity1
import com.example.bookwise.PostRequestsDataClasses.Login
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)
        val retrofitService  = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(retrofitService)
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)

        //New User Registration
        binding.newUserButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_newUserFragment)
        }

        //Handling Forget Password
        binding.forgotPasswordButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
//        binding.loginButton.setOnClickListener {
//            startActivity(Intent(this@LoginFragment.activity,MemberActivity1::class.java))
//        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailInputTextField.text.toString()
            val password = binding.passwordInputTextField.text.toString()
            val login = Login(email,password)
            viewModel.userLogin(login)
        }



        viewModel.userLoginData.observe(viewLifecycleOwner, Observer {
            if(it.sucess == true) {
                startActivity(Intent(this@LoginFragment.activity, MemberActivity1::class.java))
            }
            else{
                binding.wrongCredentialsTextView.text = "Wrong Credentials!"
                binding.emailInputTextField.setText("")
                binding.passwordInputTextField.setText("")

            }
        })



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}