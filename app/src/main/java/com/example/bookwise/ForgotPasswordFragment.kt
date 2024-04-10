package com.example.bookwise

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookwise.PutRequestDataClass.UserResetPassword
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentForgotPasswordBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_forgot_password, container, false)
        val retrofitService  = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(retrofitService)
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)

        binding.resetPasswordButton.setOnClickListener {
            val email = binding.resetEmailTextField.text.toString()
            val password = binding.resetPasswordTextField.text.toString()

            var ans = true
            ans = validateAll(email,"email") && validateAll(password,"password")
            if(ans) {
                val userResetPassword = UserResetPassword(email, password)
                viewModel.resetPassword(email, userResetPassword);
            }
        }

        viewModel.resetPasswordLoading.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.progressBar.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.resetPasswordData.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage("Password Reset Successfully")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            findNavController().navigate(R.id.loginFragment)
        })

        viewModel.resetPasswordErrorMessageData.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        })


        return binding.root
    }


    private fun validateAll(text1:String,type:String):Boolean{
        var case = type
        when(case.lowercase()){
            "email"->{
                if(text1 == ""){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Empty Email")
                        .setMessage("Please Enter a valid Email")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.resetEmailTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }
                else if(Patterns.EMAIL_ADDRESS.matcher(text1).matches()){
                    return true
                }
                else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Wrong Email")
                        .setMessage("Please Enter a valid Email")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.resetEmailTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }
            }
            "password"->{
                if(text1 == ""){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Empty Password")
                        .setMessage("Please Enter a valid Password")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.resetPasswordTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }
                else if(text1!= binding.resetPasswordRetypeTextField.text.toString()){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Password Mismatch")
                        .setMessage("Passwords do not match. Please check your password.")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.resetPasswordTextField.setText("")
                            binding.resetPasswordRetypeTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }


            }

        }
        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgotPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgotPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}