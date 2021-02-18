package com.erickson.listmanager.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.R
import kotlin.properties.Delegates

class ListExplorerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemId by Delegates.notNull<Int>()
    val idView: TextView = view.findViewById(R.id.item_number)
    val contentView: TextView = view.findViewById(R.id.content)
}