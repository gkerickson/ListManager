package com.erickson.listmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.ToDoListActivity.Companion.ARG_INDEX
import com.erickson.listmanager.adapters.MyExplorerListRecyclerViewAdapter
import com.erickson.listmanager.dialogs.CreateListDialogFragment
import com.erickson.listmanager.dialogs.DialogListener
import com.erickson.listmanager.dummy.DummyContent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListExplorerActivity : AppCompatActivity(), DialogListener {
    interface ListExplorerOnClickListener {
        fun onClick(index: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            this.findViewById<RecyclerView>(R.id.lists_recycler_view).let {
                it.layoutManager = LinearLayoutManager(this)
                it.adapter = MyExplorerListRecyclerViewAdapter(DummyContent.LISTS, object :
                    ListExplorerOnClickListener {
                    override fun onClick(index: Int) {
                        if (index > -1)
                            startActivity(
                                Intent(this@ListExplorerActivity, ToDoListActivity::class.java).apply {
                                    putExtra(ARG_INDEX, index)
                                }
                            )
                    }
                })
            }
        }

        this.findViewById<FloatingActionButton>(R.id.add_list).setOnClickListener {
            CreateListDialogFragment().apply {
                show(supportFragmentManager, null)
            }
        }
    }

    override fun onDialogPositiveClick(newItem: String) {
        DummyContent.LISTS.add(DummyContent.ToDoList(name = newItem))
        this.findViewById<RecyclerView>(R.id.lists_recycler_view).adapter?.notifyDataSetChanged()
    }
}