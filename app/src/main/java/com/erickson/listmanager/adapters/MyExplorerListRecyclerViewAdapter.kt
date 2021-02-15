package com.erickson.listmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.ListExplorerActivity
import com.erickson.listmanager.R
import com.erickson.listmanager.dummy.DatabaseHandler
import com.erickson.listmanager.viewholders.ListExplorerViewHolder

class MyExplorerListRecyclerViewAdapter(
    private val listsLiveData: LiveData<List<DatabaseHandler.TodoList>>,
    private val onClickListener: ListExplorerActivity.ListExplorerOnClickListener
) : RecyclerView.Adapter<ListExplorerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListExplorerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_explorer_list_item, parent, false)
        return ListExplorerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListExplorerViewHolder, position: Int) {
        listsLiveData.value?.get(position)?.let {item ->
            holder.idView.text = item.name[0].toString()
            holder.contentView.text = item.name
            holder.itemView.setOnClickListener {
                onClickListener.onClick(item.uid!!)
            }
            holder.contentView.setOnClickListener {
                onClickListener.onClick(item.uid!!)
            }
        }
    }

    override fun getItemCount(): Int = listsLiveData.value?.size ?: 0
}