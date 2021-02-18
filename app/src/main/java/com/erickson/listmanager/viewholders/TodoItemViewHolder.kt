package com.erickson.listmanager.viewholders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.R
import kotlin.properties.Delegates

class TodoItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var itemId by Delegates.notNull<Int>()
    val nameView: TextView = view.findViewById(R.id.item_text)
    val checkBox: CheckBox = view.findViewById(R.id.item_complete_check)
}