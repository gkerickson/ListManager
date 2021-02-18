package com.erickson.listmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.R
import com.erickson.listmanager.TodoListActivity
import com.erickson.listmanager.model.DatabaseHandler
import com.erickson.listmanager.viewholders.TodoItemViewHolder

class TodoListRecyclerViewAdapter(
    private val itemsLiveData: LiveData<List<DatabaseHandler.TodoItem>>,
    private val listener: TodoListActivity.TodoListOnClick
) : RecyclerView.Adapter<TodoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_add_list, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        itemsLiveData.value?.get(position)?.let { item ->
            holder.itemId = item.uid!!
            holder.nameView.text = item.name
            holder.checkBox.isChecked = item.checked
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                listener.onClick(item.uid, isChecked)
            }
        }
    }

    override fun getItemCount(): Int = itemsLiveData.value?.size ?: 0
}