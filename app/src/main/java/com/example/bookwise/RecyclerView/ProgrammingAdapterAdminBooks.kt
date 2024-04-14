package com.example.bookwise.RecyclerView

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Data.Book.BookListItem
import com.example.bookwise.R
import com.example.bookwise.Retrofit.GetAllUsers.UserList
import com.example.bookwise.Retrofit.GetAllUsers.UserListItem
import com.example.bookwise.SharedPreferenceHelper.SharedPreferencesHelper
import com.example.bookwise.Utils
import com.example.bookwise.ViewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgrammingAdapterAdminBooks(private val mainViewModel: MainViewModel) : ListAdapter<BookListItem, ProgrammingAdapterAdminBooks.ProgrammingViewHolder>(diffUtil()),Filterable {


    private var fullBookList: List<BookListItem> = listOf()

    class ProgrammingViewHolder(view: View,mainViewModel: MainViewModel) : RecyclerView.ViewHolder(view) {

        private val bookName: TextView = view.findViewById(R.id.book_name)
        private val authorName: TextView = view.findViewById(R.id.author_name)
        private val categoryName: TextView = view.findViewById(R.id.category_name)
        private val quantity: TextView = view.findViewById(R.id.quantity)
        private val button :ImageView = view.findViewById(R.id.add_button)
        private val quantityTv : TextView = view.findViewById(R.id.add_quantity_tv)
        private val increaseQuantityButton : ImageView = view.findViewById(R.id.add_quantity_button_init1)
        private var qunatityOfBook:Int = 0;
        private val decreaseQuantityButton : ImageView = view.findViewById(R.id.remove_quantity_button_init)

        fun bind(item: BookListItem,mainViewModel: MainViewModel) {
            quantityTv.isClickable = false

            CoroutineScope(Dispatchers.Main).launch {
                bookName.text = item.title
                authorName.text = item.author.name
                categoryName.text = item.genre.name
                quantity.text = item.quantity.toString()
                quantityTv.text = "0"

            }
            button.setOnClickListener {
                mainViewModel.alertToView(item.author.id,item.genre.id,quantityTv.text.toString().toInt(),item.title)
                Handler(Looper.myLooper()!!).postDelayed({
                    quantity.text = (item.quantity+qunatityOfBook).toString()
                    qunatityOfBook = 0
                    quantityTv.text = qunatityOfBook.toString()
                },500)

            }

            increaseQuantityButton.setOnClickListener{
                if(qunatityOfBook<9){
                    qunatityOfBook++
                    quantityTv.text = qunatityOfBook.toString()
                }
            }
            decreaseQuantityButton.setOnClickListener{
                if(qunatityOfBook>0){
                    qunatityOfBook--
                    quantityTv.text = qunatityOfBook.toString()
                }

            }
        }
    }

    fun updateList(list: BookList) {
        fullBookList = list
        submitList(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    fullBookList
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    fullBookList.filter { user ->
                        user.title.lowercase().contains(filterPattern)
                    }
                }

                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<BookListItem>)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item_admin, parent, false)
        return ProgrammingViewHolder(view,mainViewModel)
    }

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,mainViewModel)
    }

    companion object {
        class diffUtil : DiffUtil.ItemCallback<BookListItem>() {
            override fun areItemsTheSame(oldItem: BookListItem, newItem: BookListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookListItem, newItem: BookListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}