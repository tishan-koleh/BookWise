package com.example.bookwise.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwise.R
import com.example.bookwise.Retrofit.BorrowedBooksByCardId.BorrowedBooksByCardIdItem
import com.example.bookwise.ViewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgrammingAdapterBorrowedBookItem(private val mainViewModel: MainViewModel):ListAdapter<BorrowedBooksByCardIdItem,ProgrammingAdapterBorrowedBookItem.ProgrammingViewHolder>(diffUtil()){
    class ProgrammingViewHolder(view:View,mainViewModel: MainViewModel):RecyclerView.ViewHolder(view) {
        private val bookName: TextView = view.findViewById(R.id.book_name)
        private val authorName: TextView = view.findViewById(R.id.author_name)
        private val categoryName: TextView = view.findViewById(R.id.category_name)
        private val quantity: TextView = view.findViewById(R.id.quantity)
        private val button : ImageView = view.findViewById(R.id.add_button)
        fun bind(item: BorrowedBooksByCardIdItem, mainViewModel: MainViewModel) {
            CoroutineScope(Dispatchers.Main).launch {
                bookName.text = item.title
                authorName.text = item.author.name
                categoryName.text = item.genre.name
                quantity.text = item.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item_borrowed,parent,false)
        return ProgrammingViewHolder(view,mainViewModel)
    }

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,mainViewModel)
    }

    companion object {
        class diffUtil : DiffUtil.ItemCallback<BorrowedBooksByCardIdItem>() {
            override fun areItemsTheSame(oldItem: BorrowedBooksByCardIdItem, newItem: BorrowedBooksByCardIdItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BorrowedBooksByCardIdItem, newItem: BorrowedBooksByCardIdItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}