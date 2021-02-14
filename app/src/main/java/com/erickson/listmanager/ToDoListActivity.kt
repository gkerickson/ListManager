package com.erickson.listmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.adapters.MyExplorerListRecyclerViewAdapter
import com.erickson.listmanager.adapters.ToDoListRecyclerViewAdapter
import com.erickson.listmanager.dialogs.CreateListDialogFragment
import com.erickson.listmanager.dialogs.DialogListener
import com.erickson.listmanager.dummy.DummyContent
import com.erickson.listmanager.dummy.DummyContent.addDummyToDo

class ToDoListActivity : AppCompatActivity(), DialogListener {
    var index: Int = -1

    interface ToDoListOnClick {
        fun onClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        index = intent.getIntExtra(ARG_INDEX, -1)
        this.title = DummyContent.LISTS[index].name

        if(savedInstanceState == null) {
            this.findViewById<RecyclerView>(R.id.todo_list_recycler_view).let {
                it.layoutManager = LinearLayoutManager(this)
                it.adapter = ToDoListRecyclerViewAdapter(DummyContent.LISTS[index].content, object :
                    ToDoListOnClick {
                    override fun onClick() {
                        onBackPressed()
                    }
                })
            }
        }

        findViewById<FloatingActionButton>(R.id.add_to_do).setOnClickListener { view ->
            CreateListDialogFragment().show(supportFragmentManager, null)
        }
    }

    override fun onDialogPositiveClick(newItem: String) {
        DummyContent.LISTS[index].content.add(DummyContent.ToDoItem(newItem[0].toString(), newItem))
        this.findViewById<RecyclerView>(R.id.todo_list_recycler_view).adapter?.notifyDataSetChanged()
    }

    companion object {
        const val ARG_INDEX: String = "INDEX"
    }
}