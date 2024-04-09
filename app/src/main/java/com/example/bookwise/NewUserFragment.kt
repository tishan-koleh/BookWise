package com.example.bookwise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bookwise.Member.MemberActivity1
import com.example.bookwise.PostRequestsDataClasses.User
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.RetrofitHepler
import com.example.bookwise.ViewModels.MainVIewModelFactory
import com.example.bookwise.ViewModels.MainViewModel
import com.example.bookwise.databinding.FragmentNewUserBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewUserFragment : Fragment() {
    private lateinit var binding: FragmentNewUserBinding
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_user, container, false)

        val retrofitService  = RetrofitHepler.getInstance().create(ApiService::class.java)
        val factory = MainVIewModelFactory(retrofitService)
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)

        binding.registerButton.setOnClickListener {
            val name = binding.nameInputTextField.text.toString()
            val email = binding.emailInputTextField.text.toString()
            val adress = binding.adressInputTextField.text.toString()
            val contactNo = binding.contactNoInputTextField.text.toString()
            val password = binding.passwordInputTextField.text.toString()

            var ans : Boolean = true
            ans = validateAll(name,"name") &&
                    validateAll(email,"email") &&
                    validateAll(contactNo,"contactno") &&
                    validateAll(adress,"adress") &&
                    validateAll(password,"password")


            if(ans){
                val user = User(name,email,adress,contactNo,password)
                viewModel.userRegistration(user)
            }

        }



        viewModel.userRegistraionData.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG",it.message)
            startActivity(Intent(requireActivity(),MemberActivity1::class.java))
        })

        viewModel.isLoadingRegistration.observe(viewLifecycleOwner, Observer {
            if (it==true){
                binding.progressBar.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.errorRegistration.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG","Response observer error")
            AlertDialog.Builder(requireContext())
                .setTitle("Network Error")
                .setMessage("Please Try Again")
                .setPositiveButton("OK") { dialog, _ ->
                    binding.emailInputTextField.setText("")
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
                            binding.emailInputTextField.setText("")
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
                            binding.emailInputTextField.setText("")
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
                            binding.passwordInputTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }
                else if(text1!= binding.retypePasswordInputTextField.text.toString()){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Password Mismatch")
                        .setMessage("Passwords do not match. Please check your password.")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.passwordInputTextField.setText("")
                            binding.retypePasswordInputTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }


            }
            "name"->{
                if(text1 == ""){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Empty Name")
                        .setMessage("Please Enter a valid Name")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.nameInputTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }
            }
            "contactno"->{
                if(text1 == ""){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Empty Contact")
                        .setMessage("Please Enter a valid Contact")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.contactNoInputTextField.setText("")
                            dialog.dismiss()
                        }
                        .show()
                    return false
                }
            }
            "adress"->{
                if(text1 == ""){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Empty Adress")
                        .setMessage("Please Enter a valid Adress")
                        .setPositiveButton("OK") { dialog, _ ->
                            binding.adressInputTextField.setText("")
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
         * @return A new instance of fragment NewUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}