package com.erickson.listmanager.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.R
import com.erickson.listmanager.ToDoListActivity
import com.erickson.listmanager.dummy.DummyContent
import com.erickson.listmanager.viewholders.ToDoItemViewHolder

class ToDoListRecyclerViewAdapter(
    private val values: List<DummyContent.ToDoItem>,
    private val listener: ToDoListActivity.ToDoListOnClick
) : RecyclerView.Adapter<ToDoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_add_list, parent, false)
        return ToDoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        holder.checkBox.isChecked = item.checked
        holder.itemView.setOnClickListener {
            listener.onClick()
        }
    }

    override fun getItemCount(): Int = values.size
}