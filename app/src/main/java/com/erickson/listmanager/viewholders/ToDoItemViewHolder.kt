package com.erickson.listmanager.viewholders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.R

class ToDoItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val nameView: TextView = view.findViewById(R.id.item_text)
    val checkBox: CheckBox = view.findViewById(R.id.item_complete_check)
}