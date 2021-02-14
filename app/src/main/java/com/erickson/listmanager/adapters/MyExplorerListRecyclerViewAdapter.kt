package com.erickson.listmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.ListExplorerActivity
import com.erickson.listmanager.R
import com.erickson.listmanager.dummy.DummyContent
import com.erickson.listmanager.viewholders.ListExplorerViewHolder

class MyExplorerListRecyclerViewAdapter(
    private val values: List<DummyContent.ToDoList>,
    private val onClickListener: ListExplorerActivity.ListExplorerOnClickListener
) : RecyclerView.Adapter<ListExplorerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListExplorerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_explorer_list_item, parent, false)
        return ListExplorerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListExplorerViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.name
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
        holder.contentView.setOnClickListener {
            onClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = values.size
}