package com.example.bookwise.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwise.Data.Book.BookList
import com.example.bookwise.Data.Book.BookListItem
import com.example.bookwise.R
import com.example.bookwise.Retrofit.TransactionDetails.TransactionDetails
import com.example.bookwise.Retrofit.TransactionDetails.TransactionDetailsItem
import org.w3c.dom.Text

class ProgrammingAdapterAllTransactionDetails:ListAdapter<TransactionDetailsItem,ProgrammingAdapterAllTransactionDetails.MyViewHolderTransaction>(diffUtilTransation()),Filterable {

    private var transactionList: List<TransactionDetailsItem> = listOf()
     class MyViewHolderTransaction(view: View):RecyclerView.ViewHolder(view){
        private val name = view.findViewById<TextView>(R.id.user_name_transaction_tv)
        private val bookName = view.findViewById<TextView>(R.id.book_name)
        private val issueDate = view.findViewById<TextView>(R.id.issue_date)
        private val dueDate = view.findViewById<TextView>(R.id.due_date)
        fun bind(item:TransactionDetailsItem){
            name.text = item.user_name
            bookName.text = item.book_object.title
            issueDate.text = item.transaction_object.transaction_date.substring(0,10)
            dueDate.text = item.transaction_object.due_date.substring(0,10)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderTransaction {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transation_details,parent,false)
        return MyViewHolderTransaction(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderTransaction, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object{
        class diffUtilTransation : ItemCallback<TransactionDetailsItem>() {
            override fun areItemsTheSame(
                oldItem: TransactionDetailsItem,
                newItem: TransactionDetailsItem
            ): Boolean {
                return oldItem.transaction_object.id == newItem.transaction_object.id
            }

            override fun areContentsTheSame(
                oldItem: TransactionDetailsItem,
                newItem: TransactionDetailsItem
            ): Boolean {
                return oldItem==newItem
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    transactionList
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    transactionList.filter { user ->
                        user.user_name.lowercase().contains(filterPattern)
                    }
                }

                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<TransactionDetailsItem>)
            }
        }
    }
    fun updateList(list: TransactionDetails) {
        transactionList = list
        submitList(list)
    }
}