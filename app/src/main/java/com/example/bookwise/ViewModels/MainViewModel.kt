package com.example.bookwise.ViewModels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookwise.Authentication.Registration.Login.LoginResponse
import com.example.bookwise.Authentication.Registration.Registration
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Retrofit.PostRequestsDataClasses.Login
import com.example.bookwise.Retrofit.PostRequestsDataClasses.User
import com.example.bookwise.Retrofit.PutRequestDataClass.ResetResponse
import com.example.bookwise.Retrofit.PutRequestDataClass.UserResetPassword
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.Transaction.BorrowBookDetails
import com.example.bookwise.Retrofit.Transaction.ToCreateTransaction
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper
import com.example.bookwise.Utils
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: ApiService):ViewModel() {


    //Show Books to the Member

    private val bookLiveData = MutableLiveData<BookList?>()
    val book:LiveData<BookList?>
        get() = bookLiveData


    private val loadingLiveData = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean>
        get() = loadingLiveData


    private val errorLiveData = MutableLiveData<String>()
    val error :LiveData<String>
        get() = errorLiveData


     fun getBookList(){
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            try {
                val response = apiService.getBookList()
                if(response.isSuccessful){
                    val body = response.body()
                    bookLiveData.postValue(body)
                }
                else{
                    errorLiveData.postValue("Error ${response.errorBody()}")
                }
            }
            catch (e:Exception){
                errorLiveData.postValue("Error ${e.message}")
            }
            finally {

                    loadingLiveData.postValue(false)


            }
        }
    }


    //Show Books to the Member




    //User Registration
    private val userRegistrationLiveData = MutableLiveData<Registration>()
    val userRegistraionData:LiveData<Registration>
        get() = userRegistrationLiveData


    private val loadingLiveDataRegistration = MutableLiveData<Boolean>()
    val isLoadingRegistration:LiveData<Boolean>
        get() = loadingLiveDataRegistration


    private val errorLiveDataRegistration = MutableLiveData<String>()
    val errorRegistration :LiveData<String>
        get() = errorLiveDataRegistration

    fun userRegistration(user: User){

        Log.i("MYTAG","Inside Function")
        viewModelScope.launch {
            loadingLiveDataRegistration.postValue(true)
            try {
                val response = apiService.registerUser(user)
                if(response.isSuccessful && response.body()!=null){
                    Log.i("MYTAG","Response Successful")
                    val body = response.body()
                    userRegistrationLiveData.postValue(body!!)
                }
                else{
                    Log.i("MYTAG","Response elseblock")
                    errorLiveDataRegistration.postValue("Error ${response.errorBody()}")
                }
            }
            catch (e:Exception){
                Log.i("MYTAG","Response catchblock")
                errorLiveDataRegistration.postValue(e.message.toString())
            }
            finally {
                loadingLiveDataRegistration.postValue(false)
            }


        }
    }
    //User Registration




    //User Login
    private val userLoginLiveData = MutableLiveData<LoginResponse>()
    val userLoginData :LiveData<LoginResponse>
        get() = userLoginLiveData


    private val loadingLoginLiveData = MutableLiveData<Boolean>()
    val loadingLoginData : LiveData<Boolean>
        get() = loadingLoginLiveData


    private val loginErrorMessageLiveData = MutableLiveData<String>()
    val loginErrorMessageData : LiveData<String>
        get() = loginErrorMessageLiveData


    fun userLogin(login: Login){
        Log.i("MYTAG","Inside userLogin Function")
        viewModelScope.launch {
            loadingLoginLiveData.postValue(true)
            try {
                val response = apiService.loginUser(login)
                if (response.isSuccessful && response.body() != null){
                    Log.i("MYTAG", response.body()!!.sucess.toString())
                    val body = response.body()
                    userLoginLiveData.postValue(body!!)
                }
                else{
                    loginErrorMessageLiveData.postValue(response.errorBody().toString())
                }
            }catch (e:Exception){
                loginErrorMessageLiveData.postValue(e.message.toString())
            }
            finally {
                loadingLoginLiveData.postValue(false)
            }
        }
    }
    //User Login



    //Password Reset

    private val resetPasswordLiveData = MutableLiveData<ResetResponse>()
    val resetPasswordData : LiveData<ResetResponse>
        get() = resetPasswordLiveData

    private val resetPasswordLoadingLiveData = MutableLiveData<Boolean>()
    val resetPasswordLoading : LiveData<Boolean>
        get() = resetPasswordLoadingLiveData

    private val resetPasswordErrorMessageLiveData = MutableLiveData<String>()
    val resetPasswordErrorMessageData : LiveData<String>
        get() = resetPasswordErrorMessageLiveData


    fun resetPassword(email:String,userResetPassword: UserResetPassword){
        resetPasswordLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                val response = apiService.resetPassword(email,userResetPassword);
                if(response.isSuccessful){
                    if(response.body() == null){
                        Log.i("MYTAG","Inside if body is null")
                        resetPasswordErrorMessageLiveData.postValue("Invalid Email")
                    }
                    else {
                        val body = response.body()
                        resetPasswordLiveData.postValue(body!!)
                    }
                }
                else if(!response.isSuccessful){
                    Log.i("MYTAG","Inside Else outside")
                    resetPasswordErrorMessageLiveData.postValue("Email Not Found, Please create an account!")
                }
            }
            catch (e:Exception){
                Log.i("MYTAG","Inside Catch")
                resetPasswordErrorMessageLiveData.postValue("Network Error")
            }
            finally {
                resetPasswordLoadingLiveData.postValue(false)
            }
        }
    }
    // Reset Password


    //Get Card Details

    private val cardDetailsLiveData = MutableLiveData<com.example.bookwise.Retrofit.GetCardsByUserId.CardDetails>()
    val cardDetails:LiveData<com.example.bookwise.Retrofit.GetCardsByUserId.CardDetails>
        get() = cardDetailsLiveData


    private val cardLodingLiveData = MutableLiveData<Boolean>()
    val cardLoading:LiveData<Boolean>
        get() = cardLodingLiveData

    private val cardErrorMessageLiveData = MutableLiveData<String>()
    val cardErrorDetails:LiveData<String>
        get() = cardErrorMessageLiveData

    fun getCardDetails(id:Int){
        Log.i("CardDEtails","Inside Func")
        cardLodingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                val response = apiService.getCardDetails(id)
                if (response.isSuccessful){
                    Log.i("Inside Func","INSIDE-IF")
                    val body = response.body()
                    cardDetailsLiveData.postValue(body!!)
                }else{
                    Log.i("INSIDE-ELSE","${response.isSuccessful}")
                    Log.i("Inside Func","INSIDE-ELSE")
                    Log.i("INSIDE-ELSE",response.errorBody().toString())
                    cardErrorMessageLiveData.postValue(response.errorBody().toString())
                }
            }
            catch (e:Exception){
                Log.i("Inside Func","INSIDE-CATCH")
                cardErrorMessageLiveData.postValue(e.message)
            }
            finally {
                Log.i("Inside Func","INSIDE-FInally")
                cardLodingLiveData.postValue(false)
            }
        }
    }

    //Card Details

    fun test(){
        Log.i("FINALLY","Inside ViewModel")
    }

    //Transaction
    private var tID: Int? = 0
    var transaction_id:Int? = null
        get() = tID

    fun initiateTransaction(card_id:Int,book_id:Int){
        viewModelScope.launch {
            try {
                val response = apiService.initiateTransaction(ToCreateTransaction(card_id))
                if(response.isSuccessful && response.body()!=null ){
                    val body = response.body()
                    tID = body?.id
                    SharedPreferencesHelper.writeInt(Utils.transaction_id,body?.id!!)
                    Log.i("TRANSACTION_ID",body?.id.toString())
                    borrowBook(book_id,tID!!)

                }
            }
            catch (e:Exception){
                Log.i("EXCEPTION",e.message.toString())
            }
        }
    }
    private val alertLiveData =MutableLiveData<Boolean>()
    val alert:LiveData<Boolean>
        get() = alertLiveData
    fun borrowBook(bookId: Int,transaction_id:Int){

            viewModelScope.launch {
                Log.i("TRANSACTION_ID IS_INCREMENED?","${transaction_id} and ${bookId}")
                val borrowBookDetails = BorrowBookDetails(bookId,transaction_id)
                val response = apiService.borrowBook(borrowBookDetails)
                if (!response.isSuccessful) {
                    response.errorBody()?.let {
                        Log.i("Error Body", it.string())
                    }
                }
                alertLiveData.postValue(false)
            }


    }



}