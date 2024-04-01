package com.example.bookwise.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.library.baseAdapters.R
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bookwise.Data.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgrammingAdapter:ListAdapter<Book,ProgrammingAdapter.ProgrammingViewHolder>(diffUtil()) {

    class ProgrammingViewHolder(view: View):RecyclerView.ViewHolder(view){

        val bookName = view.findViewById<TextView>(com.example.bookwise.R.id.book_name)
        val authorName = view.findViewById<TextView>(com.example.bookwise.R.id.author_name)
        val categoryName = view.findViewById<TextView>(com.example.bookwise.R.id.category_name)
        val quantity = view.findViewById<TextView>(com.example.bookwise.R.id.quantity)

        fun bind(item:Book){
            CoroutineScope(Dispatchers.Main).launch {
                bookName.text = item.title
                authorName.text = item.author
                categoryName.text = item.category
                quantity.text = item.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.bookwise.R.layout.book_list_items,parent,false)
        return ProgrammingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class diffUtil : ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}