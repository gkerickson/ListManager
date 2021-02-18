package com.erickson.listmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.ToDoListActivity.Companion.ARG_LIST_UID
import com.erickson.listmanager.adapters.MyExplorerListRecyclerViewAdapter
import com.erickson.listmanager.dialogs.CreateListDialogFragment
import com.erickson.listmanager.dialogs.DialogListener
import com.erickson.listmanager.model.DatabaseHandler
import com.erickson.listmanager.viewholders.ListExplorerViewHolder
import com.erickson.listmanager.viewmodels.ListExplorerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListExplorerActivity : AppCompatActivity(), DialogListener {
    private lateinit var viewModel: ListExplorerViewModel
    private val adapter: MyExplorerListRecyclerViewAdapter by lazy {
        MyExplorerListRecyclerViewAdapter(
            viewModel.lists, object : ListExplorerOnClickListener {
                override fun onClick(listId: Int) {
                    if (listId > -1)
                        startActivity(
                            Intent(
                                this@ListExplorerActivity,
                                ToDoListActivity::class.java
                            ).apply {
                                putExtra(ARG_LIST_UID, listId)
                            }
                        )
                }
            })
    }

    interface ListExplorerOnClickListener {
        fun onClick(listId: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseHandler.setup(applicationContext)

        viewModel = ViewModelProvider(this).get(ListExplorerViewModel::class.java)

        if (savedInstanceState == null) {
            this.findViewById<RecyclerView>(R.id.lists_recycler_view).also {
                it.layoutManager = LinearLayoutManager(this)
                it.adapter = adapter
                ItemTouchHelper(CustomTouchHelper()).attachToRecyclerView(it)
            }
        }

        viewModel.lists.observe(this, Observer {
            this.findViewById<RecyclerView>(R.id.lists_recycler_view).adapter?.notifyDataSetChanged()
        })

        this.findViewById<FloatingActionButton>(R.id.add_list).setOnClickListener {
            CreateListDialogFragment().apply {
                show(supportFragmentManager, null)
            }
        }
    }

    private inner class CustomTouchHelper :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteList((viewHolder as ListExplorerViewHolder).itemId)
        }
    }

    override fun onDialogPositiveClick(newItem: String) {
        viewModel.addList(newItem)
        this.findViewById<RecyclerView>(R.id.lists_recycler_view).adapter?.notifyDataSetChanged()
    }
}