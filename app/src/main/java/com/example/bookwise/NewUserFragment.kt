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
            ans = validateEmpty(name,email,adress,contactNo,password) &&
                    validateEmail(email) &&
                    validatePassword(password,binding.retypePasswordInputTextField.text.toString())
            if(ans){
                val user = User(name,email,adress,contactNo,password)
                viewModel.userRegistration(user)
            }

        }

//        viewModel.isLoadingRegistration.observe(viewLifecycleOwner, Observer {
//            if(it == true){
//                binding.progressBar.visibility = View.VISIBLE
//            }else{
//                binding.progressBar.visibility = View.VISIBLE
//            }
//        })

        viewModel.userRegistraionData.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG",it.message)
            startActivity(Intent(this@NewUserFragment.requireContext(),MemberActivity1::class.java))
        })

        viewModel.errorRegistration.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG",it.toString())
            Toast.makeText(this@NewUserFragment.requireContext(),"Some Error Occured",Toast.LENGTH_LONG).show()
        })


        return binding.root
    }

    private fun validateEmpty(text:String,text1:String,text2:String,text3:String,text4:String):Boolean{
        if(text == ""||text1 == ""||text2 == ""||text3 == ""||text4 == ""){
            AlertDialog.Builder(requireContext())
                .setTitle("Empty Fields")
                .setMessage("Please Enter Some Value")
                .setPositiveButton("OK") { dialog, _ ->
                    binding.emailInputTextField.setText("")
                    dialog.dismiss()
                }
                .show()
            return false
        }

        return true
    }
    private fun validateEmail(text:String):Boolean{
        if(Patterns.EMAIL_ADDRESS.matcher(text).matches()){
            return true
        }
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

    private fun validatePassword(text1:String,text2:String):Boolean{
        if(text1!= text2){
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