package com.erickson.listmanager

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.erickson.listmanager.dummy.DummyContent

import com.erickson.listmanager.dummy.DummyContent.ToDoList

/**
 * [RecyclerView.Adapter] that can display a [ToDoList].
 * TODO: Replace the implementation with code for your data type.
 */
class MyExplorerListRecyclerViewAdapter(
    private val values: List<DummyContent.ListableItem>,
    private val onClick: ExplorerListFragment.FragmentOnClick
) : RecyclerView.Adapter<MyExplorerListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_explorer_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.name
        holder.itemView.setOnClickListener { _ ->
            onClick.onClick(position)
            Log.e("TEST","GOT IT - 1")
        }
        holder.contentView.setOnClickListener { _ ->
            onClick.onClick(position)
            Log.e("TEST","GOT IT - 2")
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}