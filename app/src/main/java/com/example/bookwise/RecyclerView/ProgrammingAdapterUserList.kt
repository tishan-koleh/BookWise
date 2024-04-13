package com.example.bookwise.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwise.R
import com.example.bookwise.Retrofit.ApiService
import com.example.bookwise.Retrofit.GetAllUsers.UserList
import com.example.bookwise.Retrofit.GetAllUsers.UserListItem
import com.example.bookwise.ViewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgrammingAdapterUserList(private val viewModel: MainViewModel): ListAdapter<UserListItem, ProgrammingAdapterUserList.MyViewHolderUser>(diffUtilUserList()), Filterable {

    private var fullUserList: List<UserListItem> = listOf()

    class MyViewHolderUser(view:View,viewModel: MainViewModel):RecyclerView.ViewHolder(view){
        private val userName: TextView = view.findViewById(R.id.user_name_tv)
        private val userEmail: TextView = view.findViewById(R.id.user_email_tv)
        private val userContact: TextView = view.findViewById(R.id.user_contact_tv)
        private val userAdress: TextView = view.findViewById(R.id.user_address_tv)
        private val suspendButton : Button = view.findViewById(R.id.suspend_button)
        fun bind(item:UserListItem,viewModel: MainViewModel){
            userName.text = item.name
            userEmail.text = item.email
            userContact.text = item.contact_no
            userAdress.text = item.address

            suspendButton.setOnClickListener{

                    viewModel.deleteUser(item.id)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderUser {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item,parent,false)
        return MyViewHolderUser(view,viewModel)
    }

    override fun onBindViewHolder(holder: MyViewHolderUser, position: Int) {
        val item = getItem(position)
        holder.bind(item,viewModel)
    }

    fun updateList(list: List<UserListItem>) {
        fullUserList = list
        submitList(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    fullUserList
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    fullUserList.filter { user ->
                        user.name.toLowerCase().contains(filterPattern)
                    }
                }

                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<UserListItem>)
            }
        }
    }

    companion object{
        class diffUtilUserList : ItemCallback<UserListItem>(){
            override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
                return newItem==oldItem
            }
        }
    }
}