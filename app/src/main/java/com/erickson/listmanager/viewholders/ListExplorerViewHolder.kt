package com.erickson.listmanager.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.R

class ListExplorerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val idView: TextView = view.findViewById(R.id.item_number)
    val contentView: TextView = view.findViewById(R.id.content)
}